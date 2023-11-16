package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import models.Task

class TasksRepository {

    val tasks get() = _tasks.asStateFlow()

    fun toggleIsDone(id: String) {
        _tasks.update { tasks ->
            tasks.map { task ->
                if(id == task.id) {
                    task.copy(isDone = !task.isDone)
                } else {
                    task
                }
            }
        }
    }

    companion object {
        private val _tasks = MutableStateFlow(
            listOf(
                Task(title = "titulo 1", description = "desc 2"),
                Task(title = "titulo 3", description = "desc 4")
            )
        )
    }

}