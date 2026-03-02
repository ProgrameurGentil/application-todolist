package com.example.note_haie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note_haie.R
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.label
import com.example.note_haie.ui.theme.Black
import com.example.note_haie.ui.theme.DarkBlue
import com.example.note_haie.ui.theme.Grey
import com.example.note_haie.ui.theme.LightNightBlue
import com.example.note_haie.ui.theme.LightRed
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.ui.theme.Red
import com.example.note_haie.ui.theme.White
import com.example.note_haie.utils.unixToUtc
import java.util.Calendar

@Composable
fun HeaderView(showParameter: Boolean = true) {
    val horizontalArrangement = if (showParameter)  Arrangement.SpaceBetween else  Arrangement.Start
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(DarkBlue)) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_note_haie),
                contentDescription = "Logo note haie",
                modifier = Modifier.size(40.dp)
            )

            if (showParameter) {
                Image(
                    painter = painterResource(id = R.drawable.icon_parametres),
                    contentDescription = "Icon des parametres",
                    modifier = Modifier.size(32.dp)
                )
            }

        }
    }
}

@Composable
fun FooterView() {
    Spacer(
        modifier = Modifier
            .height(64.dp)
            .background(DarkBlue)
            .fillMaxWidth()
    )
}

@Composable
fun FloatingButton(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Bouton d'ajout de tache")
    }
}

@Composable
fun TitleEntryView(question: String, isRequired: Boolean, textColor: Color = White) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = textColor)) {
                append(question)
            }
            if (isRequired) {
                withStyle(style = SpanStyle(color = Red)) {
                    append(" *")
                }
            }
        },
        modifier = Modifier.padding(vertical = 5.dp),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun EntryView(question: String, placeholder: String, isRequired: Boolean, setResponse: (String) -> Unit, textColor: Color = White) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        TitleEntryView(question = question, isRequired = isRequired, textColor = textColor)

        TextField(
            value = text,
            onValueChange = { newText -> if (!newText.contains("\n")) {
                text = newText
                setResponse(newText)
            } },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = {Text(text = placeholder)},
            colors = TextFieldDefaults.colors(
                // Couleur du fond (container)
                focusedContainerColor = LightNightBlue,
                unfocusedContainerColor = LightNightBlue.copy(alpha = 0.5f),

                // Couleur de la bordure (ou de l'indicateur bas)
                focusedIndicatorColor = LightNightBlue.copy(alpha = 0.5f),
                unfocusedIndicatorColor = LightNightBlue,

                // Couleur du texte et du curseur
                focusedTextColor = White,
                cursorColor = Color.Blue
            ),
            minLines = 1,
            maxLines = 1
        )
    }
}

@Composable
fun BigEntryView(question: String, placeholder: String, isRequired: Boolean, setResponse: (String) -> Unit, textColor: Color = White) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        TitleEntryView(question = question, isRequired = isRequired, textColor = textColor)

        TextField(
            value = text,
            onValueChange = { newText -> if (!newText.contains("\n") && newText.length <= 150) {
                text = newText
                setResponse(newText)
            } },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = {Text(text = placeholder)},
            colors = TextFieldDefaults.colors(
                // Couleur du fond (container)
                focusedContainerColor = LightNightBlue,
                unfocusedContainerColor = LightNightBlue.copy(alpha = 0.5f),

                // Couleur de la bordure (ou de l'indicateur bas)
                focusedIndicatorColor = LightNightBlue.copy(alpha = 0.5f),
                unfocusedIndicatorColor = LightNightBlue,

                // Couleur du texte et du curseur
                focusedTextColor = White,
                cursorColor = Color.Blue
            ),
            minLines = 4,
            maxLines = 4,
            supportingText = {
                Text(
                    text = "${text.length} / 150",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBoxView(question: String, isRequired: Boolean, setSelectedOption: (EnumPeriodicyTask) -> Unit, textColor: Color = White) {
    val options = EnumPeriodicyTask.entries
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0].label) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        TitleEntryView(question = question, isRequired = isRequired, textColor = textColor)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    // Couleur du fond (container)
                    focusedContainerColor = LightNightBlue,
                    unfocusedContainerColor = LightNightBlue.copy(alpha = 0.5f),

                    // Couleur de la bordure (ou de l'indicateur bas)
                    focusedIndicatorColor = LightNightBlue.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = LightNightBlue,

                    // Couleur du texte et du curseur
                    focusedTextColor = White,
                    cursorColor = Color.Blue
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selection ->
                    DropdownMenuItem(
                        text = { Text(selection.label) },
                        onClick = {
                            setSelectedOption(selection)
                            selectedOption = selection.label
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTimeView(setDateResponse: (Long) -> Unit, setHourResponse: (Int) -> Unit, setMinuteResponse: (Int) -> Unit, dateIsRequired: Boolean, timeIsRequired: Boolean) {
    var showDatePickerModal by remember { mutableStateOf(false) }
    var showTimePickerModal by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf<Int?>(null) }
    var selectedMinute by remember { mutableStateOf<Int?>(null) }

    var textDateFormat by remember { mutableStateOf(if (dateIsRequired) {
        "Cliquez pour chosir"
    } else {
        ""
    }) }
    var textTimeFormat by remember { mutableStateOf(if (timeIsRequired) {
        "Cliquez pour chosir"
    } else {
        ""
    }) }

    Row (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 5.dp)
    ) {

        Column (
            modifier = Modifier
                .weight(0.6f)
        ) {
            TitleEntryView(
                question = "Date :",
                isRequired = dateIsRequired
            )
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(
                        color = LightNightBlue.copy(0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(
                        enabled = true,
                        onClick = {
                            if (dateIsRequired)
                                showDatePickerModal = true
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = textDateFormat, color = Grey)
            }
        }


        Spacer(modifier = Modifier.width(10.dp))

        Column (
            modifier = Modifier
                .weight(0.5f)
        ) {
            TitleEntryView(
                question = "Heure :",
                isRequired = timeIsRequired
            )
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(
                        color = LightNightBlue.copy(0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(
                        enabled = true,
                        onClick = {
                            if (timeIsRequired)
                                showTimePickerModal = true
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = textTimeFormat, color = Grey)
            }
        }

        selectedDate?.let { textDateFormat = unixToUtc(it) }
        if (selectedHour != null && selectedMinute != null) {
            textTimeFormat = "${selectedHour}h${selectedMinute}"
        }

        if (showDatePickerModal) {
            DatePickerModal(
                onDateSelected = { date ->
                    selectedDate = date
                    selectedDate?.let{ setDateResponse(it) }
                    showDatePickerModal = false
                },
                onDismiss = {
                    showDatePickerModal = false
                }
            )
        }
        if (showTimePickerModal) {
            TimePickerModal(
                onTimeSelected = fun (hour: Int, minute: Int) {
                    selectedHour = hour
                    selectedMinute = minute

                    setHourResponse(hour)
                    setMinuteResponse(minute)

                    showTimePickerModal = false
                },
                onDismiss = {
                    showTimePickerModal = false
                }
            )
        }
    }
}

@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Selectionner")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun TimePickerModal(onTimeSelected: (Int, Int) -> Unit, onDismiss: () -> Unit) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Selectionner une heure", style = MaterialTheme.typography.bodyMedium)
        },
        text = {
            Column{
                TimePicker(
                    state = timePickerState,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text(text = "Selectioner")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(text = "Annuler")
            }
        },

    )
}

@Composable
fun LabelRequired(textColor: Color = White) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Red)) {
                append("* ")
            }
            withStyle(style = SpanStyle(color = textColor)) {
                append("Champ de saisie obligatoire")
            }
        },
        modifier = Modifier.padding(vertical = 5.dp),
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun ButtonView(text: String, colors: ButtonColors, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(0.dp)),
        onClick = onClick,
        colors = colors,
    ) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}

/* Previews */

@Preview(showBackground = false)
@Composable
fun EntryViewPreview() {
    NoteHaieTheme {
        EntryView(
            question = "Question de test :",
            placeholder = "Reponse",
            isRequired = true,
            setResponse = {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun BigEntryViewPreview() {
    NoteHaieTheme {
        BigEntryView(
            question = "Question de test :",
            placeholder = "Reponse",
            isRequired = true,
            setResponse = {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SelectBoxViewPreview() {
    NoteHaieTheme {
        SelectBoxView(
            question = "Question de test :",
            isRequired = true,
            setSelectedOption = {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SelectTimeViewPreview() {
    NoteHaieTheme {
        SelectTimeView(
            {}, {}, {}, dateIsRequired = false, timeIsRequired = true
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DatePickerModalPreview() {
    NoteHaieTheme {
        DatePickerModal(
            {}, {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun TimePickerModalPreview() {
    NoteHaieTheme {
        TimePickerModal(
            fun (a: Int, b: Int) {}, {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun LabelRequiredPreview() {
    NoteHaieTheme {
        LabelRequired(
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ButtonViewPreview() {
    NoteHaieTheme {
        ButtonView(
            text = "hello world",
            onClick = {},
            colors = ButtonColors(LightRed, Black, LightRed, LightRed)
        )
    }
}