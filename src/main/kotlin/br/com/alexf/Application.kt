package br.com.alexf

import br.com.alexf.database.DatabaseFactory
import br.com.alexf.plugins.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

// adicionar dependências
// criar conexão com banco de dados usando h2
// criar tabela para salvar tarefas
// implementar dao para comunicar com banco de dados
// utilizar modelos distintos para a tarefa, requisição, modelo da aplicaçao

fun Application.module() {
    DatabaseFactory.init()
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    configureRouting()
}
