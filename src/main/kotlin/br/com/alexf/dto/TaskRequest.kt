package br.com.alexf.dto

import br.com.alexf.models.Task
import kotlinx.serialization.Serializable

@Serializable
class TaskRequest(
    val title: String,
    val description: String
) {
    fun toTask() = Task(
        title = title,
        description = description
    )
}