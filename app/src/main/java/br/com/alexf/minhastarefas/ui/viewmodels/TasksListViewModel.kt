package br.com.alexf.minhastarefas.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.alexf.minhastarefas.repositories.TasksRepository
import br.com.alexf.minhastarefas.repositories.toTask
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TasksListViewModel(
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TasksListUiState> =
        MutableStateFlow(TasksListUiState())
    val uiState
        get() = _uiState
            .combine(repository.tasks) { uiState, tasks ->
                uiState.copy(tasks = tasks.map { it.toTask() })
            }
    private var searchJob: Job = Job()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    onTaskDoneChange = { task ->
                        viewModelScope.launch {
                            repository.toggleIsDone(task)
                        }
                    },
                    onSearchTextChange = { text ->
                        _uiState.update {
                            it.copy(searchText = text)
                        }
                        viewModelScope.launch {
                            searchText(text)
                        }
                    }
                )
            }
        }
    }

    private suspend fun searchText(text: String) {
        searchJob.cancel()
        delay(1000)
        searchJob = viewModelScope.launch {
            searchTasks(text)
                .map { entities ->
                    entities.map { it.toTask() }
                }
                .collectLatest { tasks ->
                    Log.i("TasksListViewModel", "searchText: $tasks")
                    _uiState.update {
                        it.copy(foundTasks = tasks)
                    }
                }
        }
    }

    private fun searchTasks(text: String) =
        repository.searchTasks(text)

}