package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.isUserAuthenticated
import br.com.alexf.minhastarefas.ui.screens.SignInScreen
import br.com.alexf.minhastarefas.ui.viewmodels.SignInViewModel
import org.koin.androidx.compose.koinViewModel

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(uiState.isAuthenticated) {
            isUserAuthenticated = uiState.isAuthenticated
            if (uiState.isAuthenticated) {
                onNavigateToHome()
            }
        }
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                viewModel.authenticate()
            },
            onSignUpClick = onNavigateToSignIn
        )
    }

}

fun NavHostController.navigateToSignIn() {
    navigate(signInRoute) {
        popUpTo(signInRoute) {
            inclusive = false
        }
    }
}
