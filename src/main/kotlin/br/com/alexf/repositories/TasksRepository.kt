package br.com.alexf.repositories

import br.com.alexf.database.dao.TaskDao
import br.com.alexf.models.Task

class TasksRepository(
    private val dao: TaskDao = TaskDao()
){

    suspend fun tasks() = dao.findAll()

    suspend fun save(task: Task) = dao.save(task)

}