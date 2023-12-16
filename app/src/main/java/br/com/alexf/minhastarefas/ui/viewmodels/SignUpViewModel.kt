package br.com.alexf.minhastarefas.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alexf.minhastarefas.ui.states.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
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
                },
                onConfirmPasswordChange = { password ->
                    _uiState.update {
                        it.copy(confirmPassword = password)
                    }
                }
            )
        }
    }

}
