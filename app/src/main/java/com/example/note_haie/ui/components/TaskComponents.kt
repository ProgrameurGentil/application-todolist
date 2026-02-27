package com.example.note_haie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.note_haie.model.EnumStateTimeTask
import com.example.note_haie.model.Task
import com.example.note_haie.model.label
import com.example.note_haie.ui.theme.Black
import com.example.note_haie.ui.theme.Green
import com.example.note_haie.ui.theme.Grey
import com.example.note_haie.ui.theme.LightNightBlue
import com.example.note_haie.ui.theme.LightRed
import com.example.note_haie.ui.theme.Red
import com.example.note_haie.ui.theme.White

@Composable
fun TaskView(task: Task) {
    val stateTime = task.stateTime
    val name = task.name
    val date = task.date

    val bgTimeStateIndicator = when (stateTime) {
        EnumStateTimeTask.LATE -> Red
        EnumStateTimeTask.NONE -> Grey
        else -> Green
    }

    var checked by remember { task.isValidated }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(LightNightBlue)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.scale(1.5f)
            )
        }
        Spacer(modifier = Modifier
            .width(5.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .background(bgTimeStateIndicator)
                        .border(1.dp, Black)
                        .fillMaxHeight()
                )

                Spacer(modifier = Modifier
                    .width(10.dp)
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = White
                )
            }

            Spacer(modifier = Modifier
                .width(2.dp)
            )

            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                color = White
            )
        }
    }
}

@Composable
fun TaskButton(task: Task, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(0.dp),
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape
    ) {
        TaskView(task)
    }
}

@Composable
fun PanelTask(task: Task?, isVisible: Boolean, onDismiss: () -> Unit) {
    if (isVisible) {
        var checked by remember { task?.isValidated ?: mutableStateOf(false) }

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight(),
            containerColor = LightNightBlue,
            contentColor = Black,
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .fillMaxHeight()
                            ,
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { checked = it },
                                modifier = Modifier.scale(1.5f)
                            )
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = task?.name ?: "Nom par défaut",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = task?.date ?: "Aucune date",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = task?.description ?: "Description par defaut",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }

                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 20.dp))
                    if (task != null && task.stateTime != EnumStateTimeTask.NONE) {
                        Text(
                            text = "État : ${task.stateTime.label}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { },
                        colors = ButtonColors(LightRed, Black, LightRed, LightRed)
                    ) {
                        Text(
                            text = "Supprimer",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { },
                        colors = ButtonColors(LightNightBlue, Black, LightNightBlue, LightNightBlue)
                    ) {
                        Text(
                            text = "Modifier",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}
