package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.ui.navigation.navigateToEditTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToNewTaskForm
import br.com.alexf.minhastarefas.ui.navigation.taskFormScreen
import br.com.alexf.minhastarefas.ui.navigation.tasksListScreen
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

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
                        tasksListScreen(
                            onNavigateToNewTaskForm = {
                                navController.navigateToNewTaskForm()
                            },
                            onNavigateToEditTaskForm = { task ->
                                navController.navigateToEditTaskForm(task)
                            }
                        )
                        taskFormScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }

}