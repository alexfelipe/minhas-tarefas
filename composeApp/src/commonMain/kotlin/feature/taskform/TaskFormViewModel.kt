package feature.taskform

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import models.Task
import moe.tlaster.precompose.viewmodel.ViewModel
import repositories.TasksRepository

class TaskFormViewModel(
    private val id: String? = null,
    private val repository: TasksRepository = TasksRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskFormUiState())

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
                topAppBarTitle = "Criando tarefa"
            )
        }
        id?.let {
            repository.findById(id)?.let { task ->
                _uiState.update { currentState ->
                    currentState.copy(
                        title = task.title,
                        description = task.description ?: "",
                        topAppBarTitle = "Editando tarefa",
                        isDeleteEnable = true
                    )
                }
            }
        }
    }

    fun save() {
        with(_uiState.value) {
            val task = Task(
                title = title,
                description = description
            )
            repository.save(task)
        }
    }

    fun delete() {
        id?.let {
            repository.delete(id)
        }
    }
}
