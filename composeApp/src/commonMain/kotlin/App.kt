import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import feature.taskform.TaskFormScreen
import feature.taskform.TaskFormUiState
import feature.taskslist.TasksListScreen
import feature.taskslist.TasksListUiState
import models.Task
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

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
                    TasksListScreen(
                        uiState = TasksListUiState(
                            tasks = listOf(
                                Task(
                                    title = "teste",
                                    description = "teste"
                                ),
                                Task(
                                    title = "teste 1 ",
                                    description = "teste 2",
                                    isDone = true
                                ),
                            ),
                        ),
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