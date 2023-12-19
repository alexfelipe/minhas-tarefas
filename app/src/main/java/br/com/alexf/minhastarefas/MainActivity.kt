package br.com.alexf.minhastarefas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.ui.navigation.authGraph
import br.com.alexf.minhastarefas.ui.navigation.authGraphRoute
import br.com.alexf.minhastarefas.ui.navigation.homeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToEditTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToHomeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToNewTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignIn
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignUp
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
                        startDestination = authGraphRoute
                    ) {
                        authGraph(
                            onNavigateToHomeGraph = {
                                navController.navigateToHomeGraph()
                            }, onNavigateToSignIn = {
                                navController.navigateToSignIn()
                            },
                            onNavigateToSignUp = {
                                navController.navigateToSignUp()
                            }
                        )
                        homeGraph(
                            onNavigateToNewTaskForm = {
                                navController.navigateToNewTaskForm()
                            }, onNavigateToEditTaskForm = { task ->
                                navController.navigateToEditTaskForm(task)
                            }, onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

}

@SuppressLint("ComposableNaming")
@Composable
private fun debugBackStack(navController: NavHostController) {
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
            val routes = navController.currentBackStack.value.map {
                it.destination.route
            }
            Log.i("MainActivity", "onCreate: back stack - $routes")
        }
    }
}
