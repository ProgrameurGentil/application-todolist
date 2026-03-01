package com.example.note_haie.ui.screens.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note_haie.R
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.model.Task
import com.example.note_haie.ui.components.FloatingButton
import com.example.note_haie.ui.components.FooterView
import com.example.note_haie.ui.components.HeaderView
import com.example.note_haie.ui.components.PanelTask
import com.example.note_haie.ui.components.TaskButton
import com.example.note_haie.ui.theme.LightWhite
import com.example.note_haie.ui.theme.MainBackground
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.viewmodels.TaskViewModel

@Composable
fun HomeScreen(viewModel: TaskViewModel, navController: NavHostController) {
    val tasks by viewModel.fullTasks().collectAsState(initial = emptyList())

    HomeScreenContent(tasks = tasks, { navController.navigate("new-task") })
}

@Composable
fun HomeScreenContent(tasks: List<Task>, navigateToNewTask: () -> Unit) {

    var showPanelTask by remember { mutableStateOf(false) }
    var taskForPanelTask: Task? by remember { mutableStateOf(null) }

    Scaffold(
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
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var isRetract by remember { mutableStateOf(false) }
                    val arrowRetracted = if (isRetract) R.drawable.fleche_haut else R.drawable.fleche_bas
                    TextButton(
                        onClick = { isRetract = !isRetract }
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 5.dp),
                            text = "En cours",
                            style = MaterialTheme.typography.titleLarge,
                            color = LightWhite
                        )

                        Image(
                            painter = painterResource(arrowRetracted),
                            contentDescription = "fleche",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 4.dp),
                            alpha = 0.54F
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AnimatedVisibility(
                        visible = !isRetract,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        LazyColumn {
                            items(tasks) { task -> // On passe directement la liste
                                TaskButton(
                                    task = task,
                                    onClick = {
                                        showPanelTask = true
                                        taskForPanelTask = task
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                PanelTask(
                    task = taskForPanelTask,
                    isVisible = showPanelTask,
                    onDismiss = { showPanelTask = false }
                )
            }

            FooterView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NoteHaieTheme {
        HomeScreenContent(
            tasks = ExempleTask.tasks,
            {  }
        )
    }
}
