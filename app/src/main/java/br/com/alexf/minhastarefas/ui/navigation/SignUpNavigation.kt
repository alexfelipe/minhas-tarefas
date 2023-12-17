package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alexf.minhastarefas.ui.screens.SignUpScreen
import br.com.alexf.minhastarefas.ui.viewmodels.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

const val signUpRoute = "signUp"


fun NavGraphBuilder.signUpScreen(
    onNavigationToSignIn: () -> Unit
){
    composable(signUpRoute){
        val viewModel = koinViewModel<SignUpViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        SignUpScreen(
            uiState = uiState,
            onSignUpClick = {
                onNavigationToSignIn()
            }
        )
    }
}

fun NavHostController.navigateToSignUp() {
    navigate(signUpRoute)
}