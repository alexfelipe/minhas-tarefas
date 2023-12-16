package br.com.alexf.minhastarefas.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation

const val authGraphRoute = "authentication"

fun NavGraphBuilder.authenticationGraph(
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    navigation(
        startDestination = signInRoute,
        route = authGraphRoute,
    ) {
        signInScreen(
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignIn = onNavigateToSignUp
        )
        signUpScreen(
            onNavigationToSignIn = onNavigateToSignIn
        )
    }
}

fun NavController.navigateToAuth() {
    navigate(
        authGraphRoute,
        navOptions = navOptions {
            popUpTo(authGraphRoute) {
                inclusive = true
            }
        }
    )
}