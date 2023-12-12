package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.repositories.toTask
import br.com.alexf.minhastarefas.ui.states.TaskFormUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class TaskFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskFormUiState> =
        MutableStateFlow(TaskFormUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["taskId"]
    private var currentTask: Task? = null
    private var isSaving = false
    private var isDeleting = false

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTitleChange = { title ->
                    _uiState.update {
                        it.copy(title = title)
                    }
                },
                onDescriptionChange = { description ->
                    _uiState.update {
                        it.copy(description = description)
                    }
                },
                topAppBarTitle = "Criando uma tarefa"
            )
        }
        id?.let {
            viewModelScope.launch {
                repository.findById(id)
                    .filterNotNull()
                    .mapNotNull {
                        it.toTask()
                    }.collectLatest { task ->
                        currentTask = task
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "Editando tarefa",
                                title = task.title,
                                description = task.description ?: "",
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    suspend fun save() {
        if (isSaving) {
            return
        }
        isSaving = true
        _uiState.update {
            it.copy(isProcessing = true)
        }
        try {
            with(_uiState.value) {
                repository.save(
                    Task(
                        id = id ?: UUID.randomUUID().toString(),
                        title = title,
                        description = description,
                        isDone = currentTask?.isDone ?: false
                    )
                )
            }
            _uiState.update {
                it.copy(
                    isProcessing = false,
                    isSavedOrDeleted = true
                )
            }
        } catch (t: Throwable) {
            isSaving = false
            _uiState.update {
                it.copy(isProcessing = false)
            }
        }
    }

    suspend fun delete() {
        if (isDeleting) {
            return
        }
        isDeleting = true
        _uiState.update {
            it.copy(isProcessing = true)
        }
        try {
            id?.let {
                repository.delete(id)
            }
            _uiState.update {
                it.copy(
                    isProcessing = false,
                    isSavedOrDeleted = true
                )
            }
        } catch (t: Throwable) {
            isDeleting = false
            _uiState.update {
                it.copy(
                    isProcessing = false,
                )
            }
        }

    }

}

