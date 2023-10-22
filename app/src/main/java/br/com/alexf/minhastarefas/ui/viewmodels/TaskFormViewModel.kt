package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.ui.states.TaskFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskFormViewModel(
    private val repository: TasksRepository
) : ViewModel() {

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
                }
            )
        }
    }

    fun save() {
        with(_uiState.value) {
            repository.save(
                Task(
                    title = title,
                    description = description
                )
            )
        }

    }

}