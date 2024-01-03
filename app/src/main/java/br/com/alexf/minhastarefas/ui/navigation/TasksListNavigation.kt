package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.repositories.ActiveUser
import br.com.alexf.minhastarefas.ui.screens.TasksListScreen
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.UsersViewModel
import org.koin.androidx.compose.koinViewModel

const val tasksListRoute = "tasksList"

fun NavGraphBuilder.tasksListScreen(
    onNavigateToNewTaskForm: () -> Unit,
    onNavigateToEditTaskForm: (Task) -> Unit,
) {
    composable(tasksListRoute) {
        val usersViewModel = koinViewModel<UsersViewModel>()
        val viewModel = koinViewModel<TasksListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(TasksListUiState())
        val currentUser by usersViewModel.currentUser.collectAsState()
        when(currentUser) {
            ActiveUser.Loading, ActiveUser.Failed -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            else -> {
                TasksListScreen(
                    uiState = uiState,
                    onNewTaskClick = onNavigateToNewTaskForm,
                    onTaskClick = onNavigateToEditTaskForm,
                    onExitAppClick = {
                        viewModel.logout()
                    }
                )
            }
        }
    }
}

fun NavHostController.navigateToTasksList(
    navOptions: NavOptions? = null
) {
    navigate(tasksListRoute, navOptions)
}