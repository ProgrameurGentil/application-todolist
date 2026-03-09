package com.example.note_haie.ui.screens.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.note_haie.R
import com.example.note_haie.database.task.toEntity
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.model.Task
import com.example.note_haie.model.addAlarm
import com.example.note_haie.ui.components.ButtonView
import com.example.note_haie.ui.components.ErrorModal
import com.example.note_haie.ui.components.FooterView
import com.example.note_haie.ui.components.FormTask
import com.example.note_haie.ui.components.HeaderView
import com.example.note_haie.ui.theme.Black
import com.example.note_haie.ui.theme.LightGreen
import com.example.note_haie.ui.theme.LightRed
import com.example.note_haie.ui.theme.MainBackground
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.utils.decomposeUnixTime
import com.example.note_haie.utils.getDateWithUnixTime
import com.example.note_haie.utils.getUnixTimeWithDecomposedTime
import com.example.note_haie.viewmodels.TaskViewModel
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun UpdateTaskScreen(idTask: Int, viewModel: TaskViewModel, navController: NavHostController) {
    val taskFlow = remember(idTask) { viewModel.getTaskWithId(idTask) }
    val task by taskFlow.collectAsState(null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (idTask <= 0) navController.navigate("home") // si la valeur par defaut est utilisé
    //navController.navigate("home") // DEBUG
    //TODO(faire qqc si task vaut null)
    task?.let {
        UpdateTaskScreenContent(
            task = it,
            navigateToBack = {
                navController.popBackStack()
            },
            navigateToHome = {
                navController.navigate("home")
            },
            updateTask = {task ->
                scope.launch {
                    viewModel.updateTask(task.toEntity()) // retourne l'id
                }
                addAlarm(context, task = task)
            }
        )
    }

}

@Composable
fun UpdateTaskScreenContent(task: Task, navigateToBack: () -> Boolean, navigateToHome: () -> Unit, updateTask : (Task) -> Unit) {
    var titleResponse by remember { mutableStateOf<String?>(task.name) }
    var descriptionResponse by remember { mutableStateOf<String?>(task.description) }
    var periodicityResponse by remember { mutableStateOf<EnumPeriodicyTask?>(task.periodicy) }
    val date = decomposeUnixTime(task.date)
    var hourResponse by remember { mutableStateOf<Int?>(date?.hour) }
    var minuteResponse by remember { mutableStateOf<Int?>(date?.minute) }
    var dateResponse  by remember { mutableStateOf<Long?>(getDateWithUnixTime(task.date)) }

    var showModalError by remember { mutableStateOf(false) }
    var titleModalError by remember { mutableStateOf("Erreur") }
    var textModalError by remember { mutableStateOf("") }

    val errorEmptyField = stringResource(R.string.champ_vide)
    val errorEmptyFieldName = stringResource(R.string.champ_vide_nom_tache)
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderView(false)
        FormTask(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MainBackground)
                .padding(16.dp),
            title = stringResource(R.string.page_modifier_tache),
            task = task,
            setTitleResponse = {
                titleResponse = it
            },
            setDescriptionResponse = {
                descriptionResponse = it
            },
            setPeriodicyResponse = {
                periodicityResponse = it
            },
            setHourResponse = {
                hourResponse = it
            },
            setMinuteResponse = {
                minuteResponse = it
            },
            setDateResponse = {
                dateResponse = it
            },
            buttonsContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ButtonView(
                        text = stringResource(R.string.annuler),
                        colors = ButtonColors(LightRed, Black, LightRed, LightRed),
                        onClick = {
                            if (!navigateToBack())
                                navigateToHome()
                        }
                    )

                    ButtonView(
                        text = stringResource(R.string.modifier),
                        colors = ButtonColors(LightGreen, Black, LightGreen, LightGreen),
                        onClick = {
                            // TODO("faire dans les prochaines versions toutes les vérifications")

                            val title = titleResponse
                            val date = getUnixTimeWithDecomposedTime(dateResponse, hourResponse, minuteResponse)

                            if (title.isNullOrBlank()) {
                                titleModalError = errorEmptyField
                                textModalError = errorEmptyFieldName
                                showModalError = true
                            } else {
                                val newTask = Task(
                                    id = task.id,
                                    name = titleResponse ?: task.name,
                                    date = if (date == 0L) null else date, //TODO ne correspond qu'a un temps unique
                                    description = descriptionResponse ?: task.description,
                                    isValidated = task.isValidated,
                                    stateTime = task.stateTime,
                                    state = task.state,
                                    periodicy = periodicityResponse ?: task.periodicy,
                                    file = task.file
                                )
                                updateTask(newTask)
                                navigateToHome()
                            }
                        }
                    )
                }
            }
        )
        FooterView()

        if (showModalError) {
            ErrorModal(titleModalError, textModalError, {showModalError = false})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateTaskScreenPreview() {
    NoteHaieTheme {
        UpdateTaskScreenContent(ExempleTask.tasks[0],{true}, {}, { _ ->})
    }
}
