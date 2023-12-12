package br.com.alexf.minhastarefas.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import br.com.alexf.minhastarefas.models.Task
import br.com.alexf.minhastarefas.samples.generators.generateRandomTasks
import br.com.alexf.minhastarefas.ui.states.TasksListUiState
import br.com.alexf.minhastarefas.ui.theme.MinhasTarefasTheme

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun TasksListScreen(
    uiState: TasksListUiState,
    isDarkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onNewTaskClick: () -> Unit = {},
    onTaskClick: (Task) -> Unit = {},
) {
    CompositionLocalProvider {

    }
    Column(modifier) {
        var isSearchTextFieldDisplayed by remember {
            mutableStateOf(false)
        }
        TopAppBar(title = {
            AnimatedVisibility(visible = !isSearchTextFieldDisplayed) {
                Switch(
                    checked = isDarkModeEnabled,
                    onCheckedChange = onDarkModeChange,
                    thumbContent = {
                        if (isDarkModeEnabled) {
                            Icon(
                                Icons.Filled.NightsStay,
                                contentDescription = "Ícone de lua"
                            )
                        } else {
                            Icon(
                                Icons.Filled.WbSunny,
                                contentDescription = "ícone de sol",
                                tint = Color(0xFFFFEB3B)
                            )
                        }
                    },
                )
            }
        }, actions = {
            AnimatedVisibility(visible = !isSearchTextFieldDisplayed) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "ícone de buscar",
                    Modifier
                        .padding(8.dp)
                        .clickable {
                            isSearchTextFieldDisplayed = true
                        }
                )
            }
            AnimatedVisibility(visible = isSearchTextFieldDisplayed) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "ícone de fechar",
                    Modifier
                        .padding(8.dp)
                        .clickable {
                            isSearchTextFieldDisplayed = false
                            uiState.onSearchTextChange("")
                        }
                )
            }
            BasicTextField(
                value = uiState.searchText,
                onValueChange = uiState.onSearchTextChange,
                Modifier.fillMaxWidth(
                    animateFloatAsState(
                        targetValue = if (isSearchTextFieldDisplayed) 1f else 0f,
                        label = "largura animada do campo de texto"
                    ).value
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = LocalContentColor.current,
                    fontSize = 18.sp,
                ),
                decorationBox = { innerTextField ->
                    if (uiState.searchText.isEmpty()) {
                        Text(
                            text = "O que você busca?",
                            style = LocalTextStyle.current.copy(
                                fontSize = 18.sp,
                                color = Color.Gray.copy(0.5f),
                                fontStyle = FontStyle.Italic
                            )
                        )
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(LocalContentColor.current)
            )
        })
        Box {
            ExtendedFloatingActionButton(
                onClick = onNewTaskClick,
                Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add new task icon")
                    Text(text = "New Task")
                }
            }
            val tasks = uiState.foundTasks.ifEmpty { uiState.tasks }
            LazyColumn(Modifier.fillMaxSize()) {
                items(tasks) { task ->
                    var showDescription by remember {
                        mutableStateOf(false)
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .combinedClickable(onClick = {
                                showDescription = !showDescription
                            }, onLongClick = {
                                onTaskClick(task)
                            })
                    ) {
                        Box(
                            Modifier
                                .padding(
                                    vertical = 16.dp, horizontal = 8.dp
                                )
                                .size(30.dp)
                                .border(
                                    border = BorderStroke(2.dp, color = Color.Gray),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    Log.i("TasksListScreen", "$task")
                                    uiState.onTaskDoneChange(task)
                                }) {
                            if (task.isDone) {
                                Icon(
                                    Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    Modifier.size(100.dp),
                                    tint = Color.Green
                                )
                            }
                        }
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = task.title, style = TextStyle.Default.copy(
                                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                                ), overflow = TextOverflow.Ellipsis, maxLines = 2
                            )
                            task.description?.let { description ->
                                AnimatedVisibility(
                                    visible = showDescription && description.isNotBlank()
                                ) {
                                    Text(
                                        text = description,
                                        style = TextStyle.Default.copy(fontSize = 24.sp),
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 3
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksListScreenPreview() {
    MinhasTarefasTheme {
        TasksListScreen(
            uiState = TasksListUiState(
                tasks = generateRandomTasks(5),
            ),
            isDarkModeEnabled = false,
            onDarkModeChange = {}
        )
    }
}