package br.com.alexf.minhastarefas.ui.states

import br.com.alexf.minhastarefas.models.Task

data class TasksListUiState(
    val tasks: List<Task> = emptyList(),
    val foundTasks: List<Task> = emptyList(),
    val onTaskDoneChange: (Task) -> Unit = {},
    val searchText: String = "",
    val onSearchTextChange: (String) -> Unit = {},
)
