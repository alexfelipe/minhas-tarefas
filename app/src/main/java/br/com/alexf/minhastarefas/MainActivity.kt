package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.ui.screens.TaskFormScreen
import br.com.alexf.minhastarefas.ui.screens.TasksListScreen
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import br.com.alexf.minhastarefas.ui.viewmodels.TasksListViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                val navController = rememberNavController()
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
                            }
                        )
                    }
                    composable("taskForm") {
                        val viewModel = koinViewModel<TaskFormViewModel>()
                        val uiState by viewModel.uiState.collectAsState()
                        TaskFormScreen(
                            uiState = uiState,
                            onSaveClick = {
                                viewModel.save()
                                navController.popBackStack()
                            })
                    }
                }
            }
        }
    }
}


