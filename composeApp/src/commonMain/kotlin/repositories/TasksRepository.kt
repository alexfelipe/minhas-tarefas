package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import models.Task

class TasksRepository {

    val tasks get() = _tasks.asStateFlow()

    fun toggleIsDone(id: String) {
        _tasks.update { tasks ->
            tasks.map { task ->
                if (id == task.id) {
                    task.copy(isDone = !task.isDone)
                } else {
                    task
                }
            }
        }
    }

    fun findById(id: String): Task? =
        _tasks.value.firstOrNull { it.id == id }

    fun save(task: Task) {
        _tasks.update { tasks ->
            tasks + task
        }
    }

    fun delete(id: String) {
        _tasks.update { tasks ->
            tasks.filterNot {
                it.id == id
            }
        }
    }

    companion object {
        private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    }

}