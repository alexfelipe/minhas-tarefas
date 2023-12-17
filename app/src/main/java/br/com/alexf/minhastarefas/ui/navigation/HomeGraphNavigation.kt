package br.com.alexf.minhastarefas.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import br.com.alexf.minhastarefas.models.Task

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

fun NavHostController.navigateToHomeGraph() {
    navigate(homeGraphRoute)
}