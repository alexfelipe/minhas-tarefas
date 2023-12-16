package br.com.alexf.minhastarefas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.minhastarefas.ui.states.SignInUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.Done,
            contentDescription = "Ícone minhas tarefas",
            Modifier
                .clip(CircleShape)
                .size(124.dp)
                .background(Color.Green, CircleShape)
                .padding(8.dp),
            tint = LocalContentColor.current
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Minhas tarefas")
        Spacer(modifier = Modifier.size(16.dp))
        val textFieldModifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
        OutlinedTextField(
            value = uiState.user,
            onValueChange = uiState.onUserChange,
            textFieldModifier,
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "ícone de usuário"
                )
            }
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = uiState.onPasswordChange,
            textFieldModifier,
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "ícone de usuário"
                )
            },
            trailingIcon = {
                when (uiState.isShowPassword) {
                    true -> Icon(
                        Icons.Filled.Visibility,
                        contentDescription = "ícone de visível",
                        Modifier.clickable {
                            uiState.hidePassword()
                        }
                    )

                    else -> Icon(
                        Icons.Filled.VisibilityOff,
                        contentDescription = "ícone de não visível",
                        Modifier.clickable {
                            uiState.hidePassword()
                        }
                    )
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = onSignInClick,
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Entrar")
        }
        TextButton(
            onClick = onSignUpClick,
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    MinhasTarefasTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}