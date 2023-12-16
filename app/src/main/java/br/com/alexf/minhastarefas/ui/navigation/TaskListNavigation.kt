package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.isUserAuthenticated
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.ui.screens.TasksListScreen
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import org.koin.androidx.compose.koinViewModel

const val tasksListRoute = "tasksList"

fun NavGraphBuilder.tasksListScreen(
    onNavigateToNewTaskForm: () -> Unit,
    onNavigateToEditTaskForm: (Task) -> Unit,
    onNavigateToAuthentication: () -> Unit
) {
    composable(tasksListRoute) {
        val viewModel = koinViewModel<TasksListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(TasksListUiState())
        if(isUserAuthenticated){
            TasksListScreen(
                uiState = uiState,
                onNewTaskClick = onNavigateToNewTaskForm,
                onTaskClick = onNavigateToEditTaskForm
            )
        } else {
            LaunchedEffect(null) {
                onNavigateToAuthentication()
            }
        }
    }
}
