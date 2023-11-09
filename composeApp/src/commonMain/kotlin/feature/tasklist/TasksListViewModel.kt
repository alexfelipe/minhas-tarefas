package feature.tasklist

import TasksRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val repository: TasksRepository = TasksRepository()
) {

    private val _uiState: MutableStateFlow<TasksListUiState> =
        MutableStateFlow(TasksListUiState())
    val uiState
        get() = _uiState
            .combine(repository.tasks) { uiState, tasks ->
                uiState.copy(tasks = tasks)
            }

    init {
        MainScope().launch {
            _uiState.update { currentState ->
                currentState.copy(onTaskDoneChange = { task ->
                    MainScope().launch {
                        repository.toggleIsDone(task)
                    }
                })
            }
        }
    }

}