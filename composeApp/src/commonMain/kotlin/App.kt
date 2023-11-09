import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import feature.taskform.TaskFormScreen
import feature.taskform.TaskFormViewModel
import feature.tasklist.TasksListScreen
import feature.tasklist.TasksListUiState
import feature.tasklist.TasksListViewModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = "taskList",
            navTransition = NavTransition()
        ) {
            scene(
                "taskList",
                navTransition = NavTransition()
            ) {
                val viewModel = remember {
                    TasksListViewModel()
                }
                val uiState by viewModel.uiState
                    .collectAsState(TasksListUiState())
                TasksListScreen(uiState = uiState,
                    onTaskClick = { task ->
                        navigator.navigate("taskForm/${task.id}")
                    },
                    onNewTaskClick = {
                        navigator.navigate("taskForm")
                    })
            }
            scene(
                "taskForm/{taskId}?",
                navTransition = NavTransition()
            ) { backStackEntry ->
                val id: String? = backStackEntry.path<String>("taskId")
                val viewModel = remember {
                    TaskFormViewModel(id = id)
                }
                val uiState by viewModel.uiState.collectAsState()
                TaskFormScreen(uiState = uiState,
                    onDeleteClick = {
                        viewModel.delete()
                        navigator.goBack()
                    }, onSaveClick = {
                        viewModel.save()
                        navigator.goBack()
                    })
            }
        }
    }
}