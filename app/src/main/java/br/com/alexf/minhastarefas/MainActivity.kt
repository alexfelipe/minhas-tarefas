package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.connection.NetworkConnectionViewModel
import br.com.alexf.minhastarefas.connection.NetworkState
import br.com.alexf.minhastarefas.ui.navigation.authGraph
import br.com.alexf.minhastarefas.ui.navigation.homeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToAuthGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToEditTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToHomeGraph
import br.com.alexf.minhastarefas.ui.navigation.navigateToNewTaskForm
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignIn
import br.com.alexf.minhastarefas.ui.navigation.navigateToSignUp
import br.com.alexf.minhastarefas.ui.navigation.splashScreen
import br.com.alexf.minhastarefas.ui.navigation.splashScreenRoute
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.alexf.minhastarefas.ui.viewmodels.AppState
import br.com.alexf.minhastarefas.ui.viewmodels.AppViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                val navController = rememberNavController()
                val connectionViewModel = koinViewModel<NetworkConnectionViewModel>()
                val connectionState by connectionViewModel.networkState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }
                val message = when(connectionState) {
                    is NetworkState.Available, is NetworkState.CapabilitiesChanged -> "com internet"
                    NetworkState.Initialization -> null
                    is NetworkState.Lost -> "sem internet"
                }
                LaunchedEffect(message) {
                    message?.let {
                        snackbarHostState.showSnackbar(it,
                            withDismissAction = connectionState is NetworkState.Lost)
                    }
                }
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state
                    .collectAsState(initial = AppState())
                LaunchedEffect(appState) {
                    if (appState.isInitLoading) {
                        return@LaunchedEffect
                    }
                    appState.user?.let {
                        navController.navigateToHomeGraph()
                    } ?: navController.navigateToAuthGraph()
                }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = splashScreenRoute,
                        Modifier.padding(it)
                    ) {
                        splashScreen()
                        authGraph(
                            onNavigateToSignIn = {
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

}

