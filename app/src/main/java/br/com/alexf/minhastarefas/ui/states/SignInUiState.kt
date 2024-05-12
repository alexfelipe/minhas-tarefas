package br.com.alexf.minhastarefas.ui.states

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val onTogglePasswordVisibility: () -> Unit = {},
    val isAuthenticated: Boolean = false,
    val error: String? = null
)