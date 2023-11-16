package feature.taskslist

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import repositories.TasksRepository

class TasksListViewModel(
    private val repository: TasksRepository = TasksRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksListUiState())
    val uiState = _uiState
        .combine(repository.tasks) { uiState, tasks ->
            uiState.copy(tasks = tasks)
        }

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onTaskDoneChange = { task ->
                    repository.toggleIsDone(task.id)
                }
            )
        }
    }

}