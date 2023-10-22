package br.com.alexf.minhastarefas.repositories

import br.com.alexf.minhastarefas.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TasksRepository {

    val tasks: Flow<List<Task>> get() = _tasks.asStateFlow()

    fun save(task: Task) {
        _tasks.update {
            it + task
        }
    }

    fun toggleIsDone(task: Task) {
        _tasks.update { tasks ->
            tasks.map { t ->
                if (t.id == task.id) {
                    t.copy(isDone = !t.isDone)
                } else {
                    t
                }
            }
        }
    }

    companion object {
        private val _tasks: MutableStateFlow<List<Task>> =
            MutableStateFlow(emptyList())
    }

}