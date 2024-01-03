package br.com.alexf.minhastarefas.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation

const val authGraphRoute = "authGraph"

fun NavGraphBuilder.authGraph(
    onNavigateToSignUp: () -> Unit,
    onNavigateToSignIn: (NavOptions) -> Unit
) {
    navigation(
        route = authGraphRoute,
        startDestination = signInRoute
    ) {
        signInScreen(
            onNavigateToSignUp = onNavigateToSignUp,
        )
        signUpScreen(
            onNavigationToSignIn = {
                onNavigateToSignIn(navOptions {
                    popUpTo(authGraphRoute)
                })
            }
        )
    }
}

fun NavHostController.navigateToAuthGraph(
    navOptions: NavOptions? = null
) {
    navigate(authGraphRoute, navOptions)
}
