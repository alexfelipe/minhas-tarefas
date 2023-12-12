package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.minhastarefas.repositories.AppRepository
import kotlinx.coroutines.launch

class AppViewModel(
    private val repository: AppRepository
) : ViewModel() {

    val isDarkMode = repository.isDarkMode

    fun updateDarkMode(value: Boolean) {
        viewModelScope.launch {
            repository.updateDarkMode(value)
        }
    }

}