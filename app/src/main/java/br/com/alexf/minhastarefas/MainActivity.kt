package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = authGraphRoute
                ) {
                    authGraph(
                        onNavigateToHomeGraph = {
                            navController.navigateToHomeGraph(it)
                        }, onNavigateToSignIn = {
                            navController.navigateToSignIn(it)
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
