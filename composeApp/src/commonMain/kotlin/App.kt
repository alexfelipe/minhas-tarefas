import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import feature.taskform.TaskFormScreen
import feature.taskform.TaskFormUiState
import feature.taskslist.TasksListScreen
import feature.taskslist.TasksListUiState
import feature.taskslist.TasksListViewModel
import models.Task
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun App() {
    MaterialTheme {
        PreComposeApp {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = "tasksList"
            ) {
                scene("tasksList") {
                    val viewModel = viewModel(modelClass = TasksListViewModel::class) {
                        TasksListViewModel()
                    }
                    val uiState by viewModel.uiState.collectAsState(TasksListUiState())
                    TasksListScreen(
                        uiState = uiState,
                        onNewTaskClick = {
                            navigator.navigate("taskForm")
                        }
                    )
                }
                scene("taskForm") {
                    TaskFormScreen(
                        uiState = TaskFormUiState(),
                        onSaveClick = {
                            navigator.goBack()
                        },
                        onDeleteClick = {

                        }
                    )
                }
            }
        }
    }
}