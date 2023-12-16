package br.com.alexf.minhastarefas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.ui.navigation.authGraphRoute
import br.com.alexf.minhastarefas.ui.navigation.authenticationGraph
import br.com.alexf.minhastarefas.ui.navigation.homeGraph
import br.com.alexf.minhastarefas.ui.navigation.homeGraphRoute
import br.com.alexf.minhastarefas.ui.navigation.navigateToAuth
import br.com.alexf.minhastarefas.ui.navigation.navigateToEditTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToHomeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToNewTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignIn
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignUp
import br.com.alexf.minhastarefas.ui.navigation.tasksListRoute
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

var isUserAuthenticated = false

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
                        startDestination = homeGraphRoute
                    ) {
                        authenticationGraph(
                            onNavigateToSignIn = {
                                navController.navigateToSignIn()
                            },
                            onNavigateToSignUp = {
                                navController.navigateToSignUp()
                            },
                            onNavigateToHome = {
                                navController.navigateToHomeGraph()
                            }
                        )
                        homeGraph(
                            onNavigateToNewTaskForm = {
                                navController.navigateToNewTaskForm()
                            },
                            onNavigateToEditTaskForm = { task ->
                                navController.navigateToEditTaskForm(task)
                            },
                            onNavigateToAuthentication = {
                                navController.navigateToAuth()
                            },
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

