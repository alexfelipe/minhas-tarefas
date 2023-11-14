package feature.taskslist

import models.Task

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val onTaskDoneChange: (Task) -> Unit = {},
)