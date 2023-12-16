package br.com.alexf.minhastarefas.ui.states

data class SignInUiState(
    val user: String = "",
    val password: String = "",
    val onUserChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val showPassword: () -> Unit = {},
    val hidePassword: () -> Unit = {},
    val isAuthenticated: Boolean = false,
)
