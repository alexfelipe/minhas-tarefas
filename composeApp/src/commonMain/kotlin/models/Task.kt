package models

import generateUUID

data class Task(
    val id: String = generateUUID(),
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false
)