package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.alexf.minhastarefas.connection.NetworkConnectionViewModel
import br.com.alexf.minhastarefas.connection.NetworkState
import br.com.alexf.minhastarefas.ui.navigation.AuthGraph
import br.com.alexf.minhastarefas.ui.navigation.HomeGraph
import br.com.alexf.minhastarefas.ui.navigation.SignInRoute
import br.com.alexf.minhastarefas.ui.navigation.SignUpRoute
import br.com.alexf.minhastarefas.ui.navigation.SplashScreenRoute
import br.com.alexf.minhastarefas.ui.navigation.TaskFormScreen
import br.com.alexf.minhastarefas.ui.navigation.authGraph
import br.com.alexf.minhastarefas.ui.navigation.homeGraph
import br.com.alexf.minhastarefas.ui.navigation.splashScreen
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
                val message = when (connectionState) {
                    is NetworkState.Available, is NetworkState.CapabilitiesChanged -> "com internet"
                    NetworkState.Initialization -> null
                    is NetworkState.Lost -> "sem internet"
                }
                LaunchedEffect(message) {
                    message?.let {
                        snackbarHostState.showSnackbar(
                            it,
                            withDismissAction = connectionState is NetworkState.Lost
                        )
                    }
                }
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state
                    .collectAsState(initial = AppState())
                LaunchedEffect(appState) {
                    if (appState.isInitLoading) {
                        return@LaunchedEffect
                    }
                    val cleanAllBackStack = navOptions {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                    appState.user?.let {
                        navController.navigate(HomeGraph, cleanAllBackStack)
                    } ?: navController.navigate(AuthGraph, cleanAllBackStack)
                }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = SplashScreenRoute,
                        Modifier.padding(it)
                    ) {
                        splashScreen()
                        authGraph(
                            onNavigateToSignIn = { navOptions ->
                                navController.navigate(SignInRoute, navOptions)
                            },
                            onNavigateToSignUp = {
                                navController.navigate(SignUpRoute)
                            }
                        )
                        homeGraph(
                            onNavigateToNewTaskForm = {
                                navController.navigate(TaskFormScreen())
                            }, onNavigateToEditTaskForm = { task ->
                                navController.navigate(TaskFormScreen(task.id))
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

