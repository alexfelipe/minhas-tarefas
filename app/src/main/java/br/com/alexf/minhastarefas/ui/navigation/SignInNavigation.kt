package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.ui.screens.SignInScreen
import br.com.alexf.minhastarefas.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SignInRoute

fun NavGraphBuilder.signInScreen(
    onNavigateToSignUp: () -> Unit
) {
    composable<SignInRoute> {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignUpClick = onNavigateToSignUp
        )
    }

}