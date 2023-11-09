package feature.taskform

import TasksRepository
import generateUUID
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.Task

class TaskFormViewModel(
    private val id: String? = null,
    private val repository: TasksRepository = TasksRepository()
) {

    private val _uiState: MutableStateFlow<TaskFormUiState> =
        MutableStateFlow(TaskFormUiState())
    val uiState = _uiState.asStateFlow()

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
            MainScope().launch {
                repository.findById(id)
                    .filterNotNull()
                    .collectLatest { task ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "Editando tarefa",
                                title = task.title,
                                description = task.description ?: "",
                                isDeleteEnable = true
                            )
                        }
                    }
            }
        }
    }

    fun save() {
        with(_uiState.value) {
            repository.save(
                Task(
                    id = id ?: generateUUID(),
                    title = title,
                    description = description
                )
            )
        }

    }

    fun delete() {
        id?.let {
            repository.delete(id)
        }
    }

}