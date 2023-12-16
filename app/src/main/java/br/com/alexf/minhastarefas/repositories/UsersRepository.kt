package br.com.alexf.minhastarefas.repositories

import kotlinx.coroutines.flow.flow

class UsersRepository {

    fun authenticate(user: String, password: String): Boolean {
        return true
    }

}