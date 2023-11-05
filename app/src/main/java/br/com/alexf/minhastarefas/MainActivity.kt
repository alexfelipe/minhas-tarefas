package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alexf.minhastarefas.ui.screens.TaskFormScreen
import br.com.alexf.minhastarefas.ui.screens.TasksListScreen
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                val navController = rememberNavController()
                KoinAndroidContext {
                    NavHost(
                        navController = navController,
                        startDestination = "tasksList"
                    ) {
                        composable("tasksList") {
                            val viewModel = koinViewModel<TasksListViewModel>()
                            val uiState by viewModel.uiState
                                .collectAsState(TasksListUiState())
                            TasksListScreen(
                                uiState = uiState,
                                onNewTaskClick = {
                                    navController.navigate("taskForm")
                                },
                                onTaskClick = { task ->
                                    navController.navigate("taskForm?taskId=${task.id}")
                                }
                            )
                        }
                        composable("taskForm?taskId={taskId}") {
                            val taskId = navArgument("taskId") {
                                nullable = true
                            }
                            val scope = rememberCoroutineScope()
                            val viewModel = koinViewModel<TaskFormViewModel>(
                                parameters = { parametersOf(taskId) })
                            val uiState by viewModel.uiState.collectAsState()
                            TaskFormScreen(
                                uiState = uiState,
                                onSaveClick = {
                                    scope.launch {
                                        viewModel.save()
                                        navController.popBackStack()
                                    }
                                },
                                onDeleteClick = {
                                    scope.launch {
                                        viewModel.delete()
                                        navController.popBackStack()
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}