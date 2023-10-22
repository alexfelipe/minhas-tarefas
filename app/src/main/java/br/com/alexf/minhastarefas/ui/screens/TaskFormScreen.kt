package br.com.alexf.minhastarefas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.minhastarefas.ui.states.TaskFormUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme

@Composable
fun TaskFormScreen(
    uiState: TaskFormUiState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit
) {
    Column(modifier) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = uiState.topAppBarTitle,
                    Modifier.weight(1f)
                )
                Row(
                    modifier
                        .padding(8.dp)
                        .clickable { onSaveClick() }) {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "Save task icon"
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(text = "Salvar")
                }
            }
        }
        val title = uiState.title
        val description = uiState.description
        val titleFontStyle = TextStyle.Default.copy(fontSize = 24.sp)
        val descriptionFontStyle = TextStyle.Default.copy(fontSize = 18.sp)
        BasicTextField(
            value = title,
            onValueChange = uiState.onTitleChange,
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                if (title.isEmpty()) {
                    Text(
                        text = "Title",
                        style = titleFontStyle.copy(
                            color = Color.Gray.copy(alpha = 0.5f)
                        ),
                    )
                }
                innerTextField()
            },
            textStyle = titleFontStyle
        )
        Spacer(modifier = Modifier.size(16.dp))
        BasicTextField(
            value = description, onValueChange = uiState.onDescriptionChange,
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                if (description.isEmpty()) {
                    Text(
                        text = "Description",
                        style = descriptionFontStyle
                            .copy(
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                    )
                }
                innerTextField()
            },
            textStyle = descriptionFontStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskFormScreenPreview() {
    MinhasTarefasTheme {
        TaskFormScreen(
            uiState = TaskFormUiState(),
            onSaveClick = {}
        )
    }
}