package br.com.alexf.repositories

import br.com.alexf.models.Task

class TasksRepository {

    val tasks get() = _tasks.toList()

    fun save(task: Task){
        _tasks.add(task)
    }

    companion object {
        private val _tasks = mutableListOf<Task>()
    }

}