package br.com.alexf.minhastarefas.ui.navigation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.alexf.minhastarefas.ui.screens.TaskFormScreen
import br.com.alexf.minhastarefas.ui.viewmodels.TaskFormViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class TaskFormScreen(val id: String? = null)

fun NavGraphBuilder.taskFormScreen(
    onPopBackStack: () -> Unit,
) {
    composable<TaskFormScreen> { backStackEntry ->
        val taskId = backStackEntry.toRoute<TaskFormScreen>().id
        Log.i("teste", "taskFormScreen: $taskId")
        val scope = rememberCoroutineScope()
        val viewModel = koinViewModel<TaskFormViewModel>(
            parameters = { parametersOf(taskId) })
        val uiState by viewModel.uiState.collectAsState()
        TaskFormScreen(
            uiState = uiState,
            onSaveClick = {
                scope.launch {
                    viewModel.save()
                    onPopBackStack()
                }
            },
            onDeleteClick = {
                scope.launch {
                    viewModel.delete()
                    onPopBackStack()
                }
            })
    }
}