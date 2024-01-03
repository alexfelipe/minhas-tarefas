package br.com.alexf.minhastarefas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.alexf.minhastarefas.repositories.ActiveUser
import br.com.alexf.minhastarefas.ui.navigation.authGraph
import br.com.alexf.minhastarefas.ui.navigation.authGraphRoute
import br.com.alexf.minhastarefas.ui.navigation.homeGraph
import br.com.alexf.minhastarefas.ui.navigation.homeGraphRoute
import br.com.alexf.minhastarefas.ui.navigation.navigateToAuthGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToEditTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToHomeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToNewTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignIn
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignUp
import br.com.alexf.minhastarefas.ui.navigation.navigateToTasksList
import br.com.alexf.minhastarefas.ui.navigation.tasksListRoute
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.alexf.minhastarefas.ui.viewmodels.UsersViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                val navController = rememberNavController()
                KoinAndroidContext {
                    MinhasTarefasNavHost(navController)
                }
            }
        }
    }
}


@Composable
fun MinhasTarefasNavHost(
    navController: NavHostController
) {
    val usersViewModel = koinViewModel<UsersViewModel>()
    val currentUser by usersViewModel.currentUser.collectAsState()
    LaunchedEffect(currentUser) {
        when (currentUser) {
            is ActiveUser.Success -> {
                navController.navigateToHomeGraph(
                    navOptions {
                        popUpTo(authGraphRoute)
                        popUpTo(homeGraphRoute)
                    }
                )
            }
            ActiveUser.Failed -> {
                navController.navigateToAuthGraph(
                    navOptions {
                        popUpTo(homeGraphRoute)
                    }
                )
            }
            else -> {
                return@LaunchedEffect
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph(
            onNavigateToNewTaskForm = {
                navController.navigateToNewTaskForm()
            },
            onNavigateToEditTaskForm = { task ->
                navController.navigateToEditTaskForm(task)
            },
            onPopBackStack = {
                navController.popBackStack()
            },
        )
        authGraph(
            onNavigateToSignIn = {
                navController.navigateToSignIn(it)
            },
            onNavigateToSignUp = {
                navController.navigateToSignUp()
            }
        )
    }
}
