package br.com.alexf.minhastarefas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.minhastarefas.connection.NetworkConnectionViewModel
import br.com.alexf.minhastarefas.connection.NetworkState
import br.com.alexf.minhastarefas.repositories.TasksRepository
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
import br.com.alexf.minhastarefas.ui.screens.SpiritBoxScreen
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme
import br.com.alexf.minhastarefas.ui.viewmodels.AppState
import br.com.alexf.minhastarefas.ui.viewmodels.AppViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val repository by inject<TasksRepository>()

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasTarefasTheme {
                SpiritBoxScreen()
//                Column {
//                    var isPlay by remember {
//                        mutableStateOf(false)
//                    }
//                    val context = LocalContext.current
//                    SomeScreen {
//                        isPlay = !isPlay
//                    }
//
//                    val mediaItem = MediaItem.Builder()
//                        .setUri(RawResourceDataSource.buildRawResourceUri(R.raw.alex))
//                        .build()
//                    val exoPlayer = remember(context, mediaItem) {
//                        ExoPlayer.Builder(context)
//                            .build()
//                            .also { exoPlayer ->
//                                exoPlayer.setMediaItem(mediaItem)
//                                exoPlayer.prepare()
//                                exoPlayer.repeatMode = REPEAT_MODE_ALL
//                            }
//                    }
//                    LaunchedEffect(isPlay) {
//                        if (isPlay) {
//                            exoPlayer.play()
//                        } else {
//                            exoPlayer.pause()
//                        }
//                    }
//                    Box {
//                        AndroidView(factory = {
//                            PlayerView(context).apply {
//                                useController = false
//                                player = exoPlayer
//                            }
//                        })
//                        Text(text = "olar", Modifier.align(Alignment.Center))
//                    }
//                }
            }
        }
    }

}

@Composable
fun SomeScreen(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit
) {
    Button(onClick = onPlayClick) {
        Text(text = "teste")
    }
}


@Composable
fun App() {
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

