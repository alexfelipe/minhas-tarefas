package br.com.alexf.minhastarefas.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation

const val authGraphRoute = "authGraph"

fun NavGraphBuilder.authGraph(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    navigation(
        route = authGraphRoute,
        startDestination = signInRoute
    ) {
        signInScreen(
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToTasksList = onNavigateToHomeGraph
        )
        signUpScreen(
            onNavigationToSignIn = onNavigateToSignIn
        )
    }
}

fun NavHostController.navigateToAuthGraph(){
    navigate(authGraphRoute)
}
