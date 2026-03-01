package com.example.note_haie.ui.screens.newtask

import android.annotation.SuppressLint
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note_haie.database.task.toEntity
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.model.Task
import com.example.note_haie.ui.components.BigEntryView
import com.example.note_haie.ui.components.ButtonView
import com.example.note_haie.ui.components.EntryView
import com.example.note_haie.ui.components.FooterView
import com.example.note_haie.ui.components.FormTask
import com.example.note_haie.ui.components.HeaderView
import com.example.note_haie.ui.components.LabelRequired
import com.example.note_haie.ui.components.SelectBoxView
import com.example.note_haie.ui.components.SelectTimeView
import com.example.note_haie.ui.screens.home.HomeScreenContent
import com.example.note_haie.ui.theme.Black
import com.example.note_haie.ui.theme.Green
import com.example.note_haie.ui.theme.LightGreen
import com.example.note_haie.ui.theme.LightRed
import com.example.note_haie.ui.theme.MainBackground
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.ui.theme.Red
import com.example.note_haie.ui.theme.White
import com.example.note_haie.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun NewTaskScreen(viewModel: TaskViewModel, navController: NavHostController) {
    NewTaskScreenContent(
        addTask = {
            viewModel.insertTask(it.toEntity())
        },
        navigateToBack = {
            navController.popBackStack()
        },
        navigateToHome = {
            navController.navigate("home")
        }
    )
}

@Composable
fun NewTaskScreenContent(addTask: suspend (Task) -> Unit, navigateToBack: () -> Boolean, navigateToHome: () -> Unit) {
    var titleResponse by remember { mutableStateOf<String?>(null) }
    var descriptionResponse by remember { mutableStateOf<String?>(null) }
    var periodicityResponse by remember { mutableStateOf<EnumPeriodicyTask?>(null) }
    var hourResponse by remember { mutableStateOf<Int?>(null) }
    var minuteResponse by remember { mutableStateOf<Int?>(null) }
    var dateResponse  by remember { mutableStateOf<Long?>(null) }

    val scope = rememberCoroutineScope()

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
            title = "Ajouter une tache",
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
                        text = "Annuler",
                        colors = ButtonColors(LightRed, Black, LightRed, LightRed),
                        onClick = {
                            if (!navigateToBack())
                                navigateToHome()
                        }
                    )

                    ButtonView(
                        text = "Valider",
                        colors = ButtonColors(LightGreen, Black, LightGreen, LightGreen),
                        onClick = {
                            scope.launch {
                                addTask(ExempleTask.tasks[0]) // pour le test
                                navigateToHome()
                            }
                        }
                    )
                }
            }
        )
        FooterView()
    }
}

@Preview(showBackground = true)
@Composable
fun NewTaskScreenPreview() {
    NoteHaieTheme {
        NewTaskScreenContent({}, {true}, {})
    }
}