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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note_haie.R
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.EnumStateTimeTask
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.model.Task
import com.example.note_haie.model.label
import com.example.note_haie.model.playSong
import com.example.note_haie.ui.theme.Black
import com.example.note_haie.ui.theme.DarkBlue
import com.example.note_haie.ui.theme.Green
import com.example.note_haie.ui.theme.Grey
import com.example.note_haie.ui.theme.LightGreen
import com.example.note_haie.ui.theme.LightNightBlue
import com.example.note_haie.ui.theme.LightRed
import com.example.note_haie.ui.theme.MainBackground
import com.example.note_haie.ui.theme.NightBlue
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.ui.theme.Red
import com.example.note_haie.ui.theme.White
import com.example.note_haie.utils.decomposeUnixTime
import com.example.note_haie.utils.getDateWithUnixTime
import com.example.note_haie.utils.unixToUtc

@Composable
fun TaskView(task: Task, onValidatedTask: (Task, Boolean) -> Unit) {
    val context = LocalContext.current

    val labelNoSpecificDate = stringResource(R.string.aucune_date_def)

    val stateTime = task.stateTime
    val name = task.name
    val date = when {
        task.date != null -> unixToUtc(task.date, time = true)
        else -> labelNoSpecificDate
    }

    val bgTimeStateIndicator = when (stateTime) {
        EnumStateTimeTask.LATE -> Red
        EnumStateTimeTask.NONE -> Grey
        else -> Green
    }

    var checked by remember(task) { task.isValidated }
    val bgColor = if (checked) NightBlue else LightNightBlue

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
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
                onCheckedChange = {
                    checked = it
                    onValidatedTask(task, it)
                    if (task.isValidated.value) {
                        playSong(context)
                    }
                },
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
                    color = DarkBlue
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
                color = DarkBlue
            )
        }
    }
}

@Composable
fun TaskButton(task: Task, onClick: () -> Unit, onValidatedTask: (Task, Boolean) -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(0.dp),
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape
    ) {
        TaskView(task, onValidatedTask)
    }
}

@Composable
fun PanelTask(task: Task, onValidatedTask: (Task, Boolean) -> Unit, onDismiss: () -> Unit, onClickUpdate: () -> Unit) {
    val context = LocalContext.current

    var checked by remember(task) { task.isValidated }

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
                            onCheckedChange = {
                                    checked = it
                                    onValidatedTask(task, it)
                                    if (task.isValidated.value) {
                                        playSong(context)
                                    }
                                },
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
                        val dateTime = decomposeUnixTime(task.date ?: 0)
                        Text(
                            text = task.name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = if (task.periodicy != EnumPeriodicyTask.SINGLE) {"le ${dateTime.day} ${dateTime.month} ${dateTime.year} à ${dateTime.hour}h${dateTime.minute}"} else stringResource(R.string.date_tache_defaut),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }

                HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 20.dp))
                if (task.stateTime != EnumStateTimeTask.NONE) {
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
                        text = stringResource(R.string.supprimer),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onClickUpdate,
                    colors = ButtonColors(LightGreen, Black, LightGreen, LightGreen)
                ) {
                    Text(
                        text = stringResource(R.string.modifier),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun FormTask(
    modifier: Modifier,
    title: String,
    setTitleResponse: (String) -> Unit,
    setDescriptionResponse: (String) -> Unit,
    setPeriodicyResponse: (EnumPeriodicyTask) -> Unit,
    setDateResponse: (Long) -> Unit,
    setHourResponse: (Int) -> Unit,
    setMinuteResponse: (Int) -> Unit,
    buttonsContent: @Composable () -> Unit,
    task: Task? = null
) {

    /**
     * Avoir, je pense qu'on peux retirer les *Response et laisser le set*Response
     * Il faut voir avec la modification des taches (Version 1)
     */
    var hourResponse by remember { mutableStateOf<Int?>(null) }
    var minuteResponse by remember { mutableStateOf<Int?>(null) }
    var dateResponse  by remember { mutableStateOf<Long?>(null) }

    var dateIsRequired by remember { mutableStateOf(false) }
    var timeIsRequired by remember { mutableStateOf(false) }

    var periodicyIsSingle by remember { mutableStateOf(true) }

    val changePeriodicy = { newPeriodicy: EnumPeriodicyTask ->
        when(newPeriodicy) {
            EnumPeriodicyTask.DAILY -> {
                periodicyIsSingle = false
                dateIsRequired = false
                timeIsRequired = true
            }

            EnumPeriodicyTask.WEEKLY -> {
                periodicyIsSingle = false
                dateIsRequired = true
                timeIsRequired = true
            }

            EnumPeriodicyTask.MONTHLY -> {
                periodicyIsSingle = false
                dateIsRequired = true
                timeIsRequired = true
            }
            else -> {
                periodicyIsSingle = true
                dateIsRequired = false
                timeIsRequired = false
            }
        }
    }

    LaunchedEffect(task) {
        task?.let {
            changePeriodicy(task.periodicy)
            it.date?.let {date ->
                val dateTime = decomposeUnixTime(date)
                minuteResponse = dateTime.minute
                hourResponse = dateTime.hour
                dateResponse = getDateWithUnixTime(date)
            }
        }
    }



    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = White
            )

            Spacer(modifier = Modifier.height(20.dp))

            EntryView(
                question = stringResource(R.string.titre),
                placeholder = stringResource(R.string.titre_ici),
                isRequired = true,
                textColor = White,
                valueResponse =  task?.name ?: "",
                setResponse = {
                    setTitleResponse(it)
                }
            )

            BigEntryView(
                question = stringResource(R.string.description),
                placeholder = stringResource(R.string.description_ici),
                isRequired = false,
                textColor = White,
                valueResponse = task?.description ?: "",
                setResponse = {
                    setDescriptionResponse(it)
                }
            )

            SelectBoxView(
                question = stringResource(R.string.periodicite),
                isRequired = true,
                textColor = White,
                optionValue = task?.periodicy,
                setSelectedOption = {
                    setPeriodicyResponse(it)
                    changePeriodicy(it)
                }
            )

            if (dateIsRequired || timeIsRequired || periodicyIsSingle) {
                SelectTimeView(
                    valueDate = dateResponse,
                    setDateResponse = {
                        setDateResponse(it)
                        dateResponse = it
                    },
                    valueHour = hourResponse,
                    setHourResponse = {
                        setHourResponse(it)
                        hourResponse = it
                    },
                    valueMinute = minuteResponse,
                    setMinuteResponse = {
                        setMinuteResponse(it)
                        minuteResponse = it
                    },
                    dateIsRequired = dateIsRequired,
                    timeIsRequired = timeIsRequired,
                    show = periodicyIsSingle
                )
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }

            LabelRequired()
        }

        buttonsContent()
    }
}

@Composable
fun ErrorModal(title: String, text: String, onDismiss: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {onDismiss()}
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {}
    )
}

/* Previews */

@Preview(showBackground = false)
@Composable
fun FormTaskPreview() {
    NoteHaieTheme {
        FormTask(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainBackground)
                .padding(16.dp),
            title = "Titre du form",
            setTitleResponse = {},
            setDescriptionResponse = {},
            setPeriodicyResponse = {},
            setDateResponse = {},
            setHourResponse = {},
            setMinuteResponse = {},
            buttonsContent = {},

        )
    }
}

@Preview(showBackground = false)
@Composable
fun TaskViewPreview() {
    NoteHaieTheme {
        TaskView(
            ExempleTask.tasks[0],
            {_, _ ->}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ErrorModalPreview() {
    NoteHaieTheme {
        ErrorModal(
            "Titre de l'erreur",
            "Je suis une erreur !!",
            onDismiss = {}
        )
    }
}