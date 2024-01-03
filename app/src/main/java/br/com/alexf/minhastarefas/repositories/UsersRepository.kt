package br.com.alexf.minhastarefas.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val activeUserPreferences = stringPreferencesKey("active_user")

sealed class ActiveUser {
    data object Loading : ActiveUser()
    data object Failed : ActiveUser()
    data class Success(
        val username: String,
    ) : ActiveUser()
}

class UsersRepository(
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher
) {

    val currentUser = MutableStateFlow<ActiveUser>(
        ActiveUser.Loading
    )

    init {
        CoroutineScope(ioDispatcher).launch {
            delay(1000)
            dataStore.data.collectLatest {
                currentUser.value =
                    it[activeUserPreferences]?.let { user ->
                        ActiveUser.Success(
                            username = user
                        )
                    } ?: ActiveUser.Failed
            }
        }
    }

    fun authenticate(
        user: String,
        password: String
    ) {
        CoroutineScope(ioDispatcher).launch {
            dataStore.edit {
                it[activeUserPreferences] = user
            }
        }
    }

    fun logout() {
        CoroutineScope(ioDispatcher).launch {
            dataStore.edit {
                it.remove(activeUserPreferences)
            }
        }
    }

}
