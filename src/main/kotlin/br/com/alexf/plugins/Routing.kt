package br.com.alexf.plugins

import br.com.alexf.models.Task
import br.com.alexf.repositories.TasksRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repository = TasksRepository()
    routing {
        get("/tasks") {
            call.respond(repository.tasks)
        }
        post("/tasks") {
            val task = call.receive<Task>()
            repository.save(task)
            call.respondText(
                "Task was created",
                status = HttpStatusCode.Created
            )
        }
    }
}
