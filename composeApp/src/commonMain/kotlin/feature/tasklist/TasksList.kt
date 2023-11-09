package feature.tasklist

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import models.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksListScreen(
    uiState: TasksListUiState,
    modifier: Modifier = Modifier,
    onNewTaskClick: () -> Unit = {},
    onTaskClick: (Task) -> Unit = {},
) {
    Box(modifier) {
        ExtendedFloatingActionButton(
            onClick = onNewTaskClick,
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add new task icon")
                    Text(text = "New Task")
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        )
        LazyColumn(Modifier.fillMaxSize()) {
            items(uiState.tasks) { task ->
                var showDescription by remember {
                    mutableStateOf(false)
                }
                Row(Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            showDescription = !showDescription
                        },
                        onLongClick = {
                            onTaskClick(task)
                        }
                    )) {
                    Box(
                        Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 8.dp
                            )
                            .size(30.dp)
                            .border(
                                border = BorderStroke(2.dp, color = Color.Gray),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                uiState.onTaskDoneChange(task)
                            }
                    ) {
                        if (task.isDone) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Done icon",
                                Modifier
                                    .size(100.dp),
                                tint = Color.Green
                            )
                        }
                    }
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = task.title,
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                        task.description?.let { description ->
                            AnimatedVisibility(
                                visible = showDescription &&
                                        description.isNotBlank()
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