package com.example.note_haie.ui.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.note_haie.R
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.EnumPriorityLevel
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
                contentDescription = stringResource(R.string.description_icon_note_haie),
                modifier = Modifier.size(40.dp)
            )

            if (showParameter) {
                Image(
                    painter = painterResource(id = R.drawable.icon_parametres),
                    contentDescription = stringResource(R.string.description_icon_parametre),
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
        Icon(Icons.Filled.Add, stringResource(R.string.description_bouton_ajout_tache))
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
                    append(stringResource(R.string.mention_obligatoire_apres))
                }
            }
        },
        modifier = Modifier.padding(vertical = 5.dp),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun EntryView(question: String, placeholder: String, isRequired: Boolean, valueResponse: String="", setResponse: (String) -> Unit, textColor: Color = White) {
    var text by remember { mutableStateOf(valueResponse) }

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
fun BigEntryView(question: String, placeholder: String, isRequired: Boolean, valueResponse: String = "", setResponse: (String) -> Unit, textColor: Color = White) {
    var text by remember { mutableStateOf(valueResponse) }

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
fun SelectBoxViewPeriodicy(
    question: String,
    isRequired: Boolean,
    optionValue: EnumPeriodicyTask? = null,
    setSelectedOption: (EnumPeriodicyTask) -> Unit,
    textColor: Color = White
) {
    val options = EnumPeriodicyTask.entries
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(optionValue?.label ?: options[0].label) }

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
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
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
fun SelectBoxViewPriority(
    question: String,
    isRequired: Boolean,
    optionValue: EnumPriorityLevel? = null,
    setSelectedOption: (EnumPriorityLevel) -> Unit,
    textColor: Color = White
) {
    val options = EnumPriorityLevel.entries
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(optionValue?.label ?: options[0].label) }

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
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
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
fun SelectTimeView(
    valueDate: Long? = null,
    setDateResponse: (Long) -> Unit,
    valueHour: Int? = null,
    setHourResponse: (Int) -> Unit,
    valueMinute: Int? = null,
    setMinuteResponse: (Int) -> Unit,
    dateIsRequired: Boolean,
    timeIsRequired: Boolean,
    show: Boolean = false
) {
    val labelChoice = stringResource(R.string.cliquer_pour_choisir)
    val labelEmpty = stringResource(R.string.vide)

    var showDatePickerModal by remember { mutableStateOf(false) }
    var showTimePickerModal by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf<Int?>(null) }
    var selectedMinute by remember { mutableStateOf<Int?>(null) }

//    var textDateFormat by remember { mutableStateOf(stringResource(R.string.cliquer_pour_choisir))}
    //var textTimeFormat by remember { mutableStateOf("Cliquez pour chosir")}

    val textDateFormat = when {
        valueDate != null -> unixToUtc(valueDate)
        selectedDate != null -> selectedDate?.let { unixToUtc(it) } ?: labelChoice
        dateIsRequired || show -> labelChoice
        else -> labelEmpty
    }

    val textTimeFormat = when {
        valueHour !=null || valueMinute != null -> "${valueHour ?: 0}h${valueMinute ?: 0}"
        selectedHour != null && selectedMinute != null -> "${selectedHour}h${selectedMinute}"
        dateIsRequired || show -> labelChoice
        else -> labelEmpty
    }

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
                question = stringResource(R.string.nom_champ_date),
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
                            if (dateIsRequired || show)
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
                question = stringResource(R.string.nom_champ_heure),
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
                            if (timeIsRequired || show)
                                showTimePickerModal = true
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = textTimeFormat, color = Grey)
            }
        }

        if (showDatePickerModal) {
            DatePickerModal(
                dateValue = valueDate,
                onDateSelected = { date ->
                    date?.let {
                        selectedDate = it
                        setDateResponse(it)
                    }
                    showDatePickerModal = false
                },
                onDismiss = {
                    showDatePickerModal = false
                }
            )
        }
        if (showTimePickerModal) {
            TimePickerModal(
                valueHour = valueHour,
                valueMinute = valueMinute,
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

/**
 * dateValue est une date en milliseconde
 */
@Composable
fun DatePickerModal(dateValue: Long? = null, onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(dateValue)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.selectionner))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.annuler))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun TimePickerModal(valueHour: Int? = null, valueMinute: Int? = null, onTimeSelected: (Int, Int) -> Unit, onDismiss: () -> Unit) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = valueHour ?: currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = valueMinute ?: currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.selectionner_heure), style = MaterialTheme.typography.bodyMedium)
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
                Text(text = stringResource(R.string.selectionner))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(text = stringResource(R.string.annuler))
            }
        },

    )
}

@Composable
fun LabelRequired(textColor: Color = White) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Red)) {
                append(stringResource(R.string.mention_obligatoire_avant))
            }
            withStyle(style = SpanStyle(color = textColor)) {
                append(stringResource(R.string.mention_obligatoire))
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

@Composable
fun ImagePicker(uri: Uri? = null, onImageSelected: (String?) -> Unit) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf(uri) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uriContent ->
            var uri = uriContent
            if (uri != null) {
                try {
                    val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context.contentResolver.takePersistableUriPermission(uri, flags)
                } catch (e: Exception) {
                    uri = null // cela veut dire que le fichier n est pas supporte
                }
            }
            imageUri = uri
            onImageSelected(uri?.toString())
        }
    )
    LaunchedEffect(uri) {
        onImageSelected(uri?.toString())
    }

    Column(
        modifier = Modifier
    ) {
        TitleEntryView(question = stringResource(R.string.image), isRequired = false)
        Box(
            modifier = Modifier
                .size(200.dp)
        ) {
            if (imageUri == null) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = LightNightBlue.copy(0.5f),
                            shape = RoundedCornerShape(25.dp)
                        ),
                    shape = RoundedCornerShape(25.dp),
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.choisir_image),
                        color = White
                    )
                }
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = stringResource(R.string.desc_imgage_selectionnee),
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                TextButton(
                    onClick = {
                        imageUri = null
                        onImageSelected(null)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.croix_64),
                        contentDescription = stringResource(R.string.desc_croix)
                    )
                }
            }
        }
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
fun SelectBoxViewPeriodicyPreview() {
    NoteHaieTheme {
        SelectBoxViewPeriodicy(
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
            setDateResponse = {}, setHourResponse = {}, setMinuteResponse = {}, dateIsRequired = false, timeIsRequired = true
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DatePickerModalPreview() {
    NoteHaieTheme {
        DatePickerModal(
            onDismiss = {}, onDateSelected = {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun TimePickerModalPreview() {
    NoteHaieTheme {
        TimePickerModal(
            onTimeSelected = fun (_: Int, _: Int) {}, onDismiss = {}
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

@Preview(showBackground = false)
@Composable
fun ImagePickerPreview() {
    NoteHaieTheme {
        ImagePicker(
            onImageSelected = {}
        )
    }
}