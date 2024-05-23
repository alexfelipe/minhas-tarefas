package br.com.alexf.minhastarefas.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.alexf.minhastarefas.authentication.FirebaseAuthRepository
import br.com.alexf.minhastarefas.ui.states.SignInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                },
                onTogglePasswordVisibility = {
                    _uiState.update {
                        it.copy(isShowPassword = !_uiState.value.isShowPassword)
                    }
                }
            )
        }
    }

    suspend fun signIn() {
        try {
            firebaseAuthRepository
                .signIn(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
        } catch (e: Exception) {
            Log.e("SignInViewModel", "signIn: ", e)
            _uiState.update {
                it.copy(error = "Erro ao fazer login")
            }
            delay(3000)
            _uiState.update {
                it.copy(error = null)
            }
        }
    }

}