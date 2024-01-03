package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alexf.minhastarefas.repositories.UsersRepository
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel(
    repository: UsersRepository
) : ViewModel() {

    val currentUser = repository.currentUser.asStateFlow()

}