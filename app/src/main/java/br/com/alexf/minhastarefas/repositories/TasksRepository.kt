package br.com.alexf.minhastarefas.repositories

import android.util.Log
import br.com.alexf.minhastarefas.database.dao.TasksDao
import br.com.alexf.minhastarefas.database.entities.TaskEntity
import br.com.alexf.minhastarefas.models.Task
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TasksRepository(
    private val dao: TasksDao
) {

    val tasks = dao.findAll()

    suspend fun save(task: Task) {
        withContext(IO) {
            dao.save(task.toTaskEntity())
        }
    }

    suspend fun toggleIsDone(task: Task) {
        val entity = task
            .copy(isDone = !task.isDone)
            .toTaskEntity()
        withContext(IO) {
            dao.save(entity)
        }
    }

}

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)

fun TaskEntity.toTask() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    isDone = this.isDone
)