package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.ui.screens.TasksListScreen
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object TasksListRoute

fun NavGraphBuilder.tasksListScreen(
    onNavigateToNewTaskForm: () -> Unit,
    onNavigateToEditTaskForm: (Task) -> Unit,
) {
    composable<TasksListRoute> {
        val viewModel = koinViewModel<TasksListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(TasksListUiState())
        TasksListScreen(
            uiState = uiState,
            onNewTaskClick = onNavigateToNewTaskForm,
            onTaskClick = onNavigateToEditTaskForm,
            onExitToAppClick = {
                viewModel.signOut()
            }
        )
    }
}