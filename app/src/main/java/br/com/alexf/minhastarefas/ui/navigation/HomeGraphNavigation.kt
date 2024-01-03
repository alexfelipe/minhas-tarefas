package br.com.alexf.minhastarefas.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.repositories.ActiveUser
import br.com.alexf.minhastarefas.ui.viewmodels.UsersViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    onNavigateToNewTaskForm: () -> Unit,
    onNavigateToEditTaskForm: (Task) -> Unit,
    onPopBackStack: () -> Unit
) {
    navigation(
        startDestination = tasksListRoute,
        route = homeGraphRoute
    ) {
        tasksListScreen(
            onNavigateToNewTaskForm = onNavigateToNewTaskForm,
            onNavigateToEditTaskForm = onNavigateToEditTaskForm
        )
        taskFormScreen(onPopBackStack = onPopBackStack)
    }
}

fun NavHostController.navigateToHomeGraph(
    navOptions: NavOptions? = null
) {
    navigate(homeGraphRoute, navOptions)
}