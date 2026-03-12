package com.example.note_haie.ui.screens.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.note_haie.R
import com.example.note_haie.database.task.toEntity
import com.example.note_haie.model.EnumStateTask
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.model.Task
import com.example.note_haie.model.copyTask
import com.example.note_haie.model.updateDateWithPeriodicy
import com.example.note_haie.ui.components.ConfirmModal
import com.example.note_haie.ui.components.FloatingButton
import com.example.note_haie.ui.components.FooterView
import com.example.note_haie.ui.components.HeaderView
import com.example.note_haie.ui.components.JokeModal
import com.example.note_haie.ui.components.PanelTask
import com.example.note_haie.ui.components.TaskButton
import com.example.note_haie.ui.theme.LightWhite
import com.example.note_haie.ui.theme.MainBackground
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.utils.actualDate
import com.example.note_haie.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: TaskViewModel, navController: NavHostController) {
    val tasksInProgress by viewModel.notValidatedTask().collectAsState(initial = emptyList())
    val tasksFinished by viewModel.validatedTask().collectAsState(initial = emptyList())

    var showJoke by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(tasksFinished) {
        tasksFinished.let {
            it.forEach { task ->
                task.date?.let {date -> // si une date est definit
                    val newDate = updateDateWithPeriodicy(task.periodicy, task.date, task.dateValidated)

                    if (date != newDate) { // si la date est differente alors cela signifi qu'on est un autre jour
                        scope.launch {
                            viewModel.updateTask(
                                copyTask(
                                    task = task,
                                    dateValidated = null,
                                    date = newDate,
                                    isValidated = mutableStateOf(false),
                                    state = EnumStateTask.NOT_REALISED
                                ).toEntity()
                            )
                        }
                    }
                }
            }
        }
    }

    HomeScreenContent(
        tasksInProgress = tasksInProgress,
        tasksFinished = tasksFinished,
        onValidatedTask = { task, newValue ->
            scope.launch {
                val newTask = copyTask(
                    task = task,
                    isValidated = mutableStateOf(newValue),
                    dateValidated = if (newValue) actualDate() else null
                )
                viewModel.updateTask(newTask.toEntity())
            }
            showJoke = newValue
        },
        navigateToNewTask = {
            navController.navigate("new-task")
        },
        navigateToUpDateTask = { id ->
            if (id <= 0) navController.navigate("home")
            navController.navigate("update-task/$id")
        },
        deleteTask = { id ->
            scope.launch {
                viewModel.deleteTask(id)
            }
        }
    )

    if (showJoke) {
        JokeModal(onDismiss = {showJoke = false})
    }
}

@Composable
fun HomeScreenContent(
    tasksInProgress: List<Task>,
    tasksFinished: List<Task>,
    onValidatedTask: (Task, Boolean) -> Unit,
    navigateToNewTask: () -> Unit,
    navigateToUpDateTask: (Int) -> Unit,
    deleteTask: (Int) -> Unit
) {

    var taskSelected by remember { mutableStateOf<Task?>(null) }
    var showPermission by remember { mutableStateOf(true) }
    var showConfirmModal by remember { mutableStateOf(false) }

    if (showPermission) {
        PermissionScreen(onResult = {showPermission = false})
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingButton(
                onClick = navigateToNewTask
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MainBackground),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderView()

            Column(
                modifier = Modifier
                    .weight(1f) // Prend tout l'espace disponible entre Header et Footer
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var isRetractInProgress by remember { mutableStateOf(false) }
                    val arrowRetractedInProgress = if (isRetractInProgress) R.drawable.fleche_haut else R.drawable.fleche_bas

                    TextButton(
                        onClick = { isRetractInProgress = !isRetractInProgress }
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 5.dp),
                            text = stringResource(R.string.en_cours),
                            style = MaterialTheme.typography.titleLarge,
                            color = LightWhite
                        )

                        Image(
                            painter = painterResource(arrowRetractedInProgress),
                            contentDescription = stringResource(R.string.description_icon_fleche),
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 4.dp),
                            alpha = 0.54F
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AnimatedVisibility(
                        visible = !isRetractInProgress,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            tasksInProgress.forEach { task ->
                                TaskButton(
                                    task = task,
                                    onClick = {
                                        taskSelected = task
                                    },
                                    onValidatedTask = onValidatedTask
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    var isRetractFinish by remember { mutableStateOf(false) }
                    val arrowRetractedFinish = if (isRetractFinish) R.drawable.fleche_haut else R.drawable.fleche_bas

                    TextButton(
                        onClick = { isRetractFinish = !isRetractFinish }
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 5.dp),
                            text = stringResource(R.string.termine),
                            style = MaterialTheme.typography.titleLarge,
                            color = LightWhite
                        )

                        Image(
                            painter = painterResource(arrowRetractedFinish),
                            contentDescription = stringResource(R.string.description_icon_fleche),
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 4.dp),
                            alpha = 0.54F
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AnimatedVisibility(
                        visible = !isRetractFinish,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            tasksFinished.forEach { task ->
                                TaskButton(
                                    task = task,
                                    onClick = {
                                        taskSelected = task
                                    },
                                    onValidatedTask = onValidatedTask
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                taskSelected?.let {task ->
                    PanelTask(
                        task = task,
                        onDismiss = { taskSelected = null },
                        onValidatedTask = onValidatedTask,
                        onClickUpdate = {
                            taskSelected = null
                            navigateToUpDateTask(task.id)
                        },
                        onClickDelete = {
                            showConfirmModal = true
                        }
                    )
                }

                if (showConfirmModal) {
                    val currentTask = taskSelected
                    if (currentTask != null) {
                        val idTaskSelected = currentTask.id
                        val nameTaskSelected = currentTask.name
                        stringResource(R.string.message_confirmation_suppr_tache)
                        ConfirmModal(
                            title = stringResource(R.string.titre_suppression_tache),
                            text = stringResource(R.string.message_confirmation_suppr_tache, nameTaskSelected),
                            onDismiss = {
                                showConfirmModal = false
                            },
                            onDismissTrue = {
                                taskSelected = null
                                deleteTask(idTaskSelected)
                            },
                            onDismissFalse = {}
                        )
                    } else {
                        showConfirmModal = false
                    }

                }

            }

            FooterView()
        }
    }
}

@Composable
fun PermissionScreen(onResult: (Boolean) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { onResult(it) }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            launcher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NoteHaieTheme {
        HomeScreenContent(
            tasksInProgress = ExempleTask.tasks,
            tasksFinished = ExempleTask.tasks.subList(0, 2),
            {_, _ -> },
            {},
            {},
            {}
        )
    }
}
