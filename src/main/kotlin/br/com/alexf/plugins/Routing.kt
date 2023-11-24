package br.com.alexf.plugins

import br.com.alexf.dto.TaskRequest
import br.com.alexf.dto.toTaskResponse
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
            val response = repository.tasks().map {
                it.toTaskResponse()
            }
            call.respond(response)
        }
        post("/tasks") {
            val request = call.receive<TaskRequest>()
            repository.save(request.toTask())?.let {
                call.respondText(
                    "Task was created",
                    status = HttpStatusCode.Created
                )
            } ?: call.respondText(
                "Task not created",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}
