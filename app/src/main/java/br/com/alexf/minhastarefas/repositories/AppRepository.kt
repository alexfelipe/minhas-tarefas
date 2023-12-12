package br.com.alexf.minhastarefas.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

val DARK_MODE = booleanPreferencesKey("DARK_MODE_PREFERENCES")

class AppRepository(
    private val dataStore: DataStore<Preferences>
) {
    val isDarkMode = dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    suspend fun updateDarkMode(value: Boolean) {
        dataStore.edit { settings ->
            settings[DARK_MODE] = value
        }
    }

}