package br.com.alexf.minhastarefas.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

fun NavGraphBuilder.authGraph(
    onNavigateToSignUp: () -> Unit,
    onNavigateToSignIn: (NavOptions) -> Unit
) {
    navigation<AuthGraph>(
        startDestination = SignInRoute
    ) {
        signInScreen(
            onNavigateToSignUp = onNavigateToSignUp,
        )
        signUpScreen(
            onNavigationToSignIn = {
                onNavigateToSignIn(navOptions {
                    popUpTo(AuthGraph)
                })
            }
        )
    }
}

fun NavHostController.navigateToAuthGraph(
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
) {
    navigate(AuthGraph, navOptions)
}
