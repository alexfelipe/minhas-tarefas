import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import models.Task
class TasksRepository() {

    val tasks get() = _tasks.asStateFlow()

    fun save(task: Task) {
        _tasks.update { currentTasks ->
            currentTasks.find {
                task.id == it.id
            }?.let {
                currentTasks.map { t ->
                    if (t.id == task.id) {
                        task
                    } else {
                        t
                    }
                }
            } ?: (currentTasks + task)
        }
    }

    fun toggleIsDone(task: Task) {
        _tasks.update { currentTasks ->
            currentTasks.map { t ->
                if (t.id == task.id) {
                    t.copy(isDone = !t.isDone)
                } else {
                    t
                }
            }
        }
    }

    fun delete(id: String) {
        _tasks.update { currentTasks ->
            currentTasks.filterNot { it.id == id }
        }

    }

    fun findById(id: String) = flow {
        _tasks.collect { tasks ->
            emit(tasks.find { id == it.id })
        }
    }


    companion object {
        private val _tasks =
            MutableStateFlow<List<Task>>(emptyList())
    }

}

