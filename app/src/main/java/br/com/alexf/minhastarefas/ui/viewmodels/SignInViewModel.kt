package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alexf.minhastarefas.repositories.UsersRepository
import br.com.alexf.minhastarefas.ui.states.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val repository: UsersRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onUserChange = { user ->
                    _uiState.update {
                        it.copy(user = user)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                }
            )
        }
    }

    fun authenticate() {
        with(_uiState.value) {
            repository.authenticate(user, password)
        }
    }

}