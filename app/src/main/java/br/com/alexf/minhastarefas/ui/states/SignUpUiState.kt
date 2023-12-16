package br.com.alexf.minhastarefas.ui.states

data class SignUpUiState(
    val user: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onUserChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {}
)
