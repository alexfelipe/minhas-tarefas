package br.com.alexf.minhastarefas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
            var isDarkModeEnabled by remember {
                mutableStateOf(true)
            }
            MinhasTarefasTheme(darkTheme = isDarkModeEnabled) {
                Surface(color = MaterialTheme.colorScheme.background) {
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
                                },
                                isDarkModeEnabled = isDarkModeEnabled,
                                onDarkModeChange = {
                                    isDarkModeEnabled = it
                                }
                            )
                            taskFormScreen(
                                onPopBackStack = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}