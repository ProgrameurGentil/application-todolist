package com.example.note_haie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note_haie.database.AppDatabase
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.ui.screens.home.HomeScreen
import com.example.note_haie.ui.screens.home.HomeScreenContent
import com.example.note_haie.ui.screens.newtask.NewTaskScreen
import com.example.note_haie.ui.screens.newtask.NewTaskScreenContent
import com.example.note_haie.ui.screens.update.UpdateTaskScreen
import com.example.note_haie.ui.screens.update.UpdateTaskScreenContent
import com.example.note_haie.ui.theme.NoteHaieTheme
import com.example.note_haie.viewmodels.TaskViewModel
import com.example.note_haie.viewmodels.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val dao = database.taskDao()

        enableEdgeToEdge()
        setContent {
            NoteHaieTheme {
                val viewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(dao)
                )
                AppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    // Définir le système de navigation
    NavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(800)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(800)
            )
        }
    ) {
        composable("home") {
            HomeScreen(viewModel, navController)
        }
        composable("new-task") {
            NewTaskScreen(viewModel, navController)
        }
        composable("parameter") {
            ParameterScreen()
        }
        composable("update-task/{idTask}",
            arguments = listOf(
                navArgument("idTask") {type = NavType.IntType},
            )
        ) {backStackEntry ->
            val idTask = backStackEntry.arguments?.getInt("idTask") ?: 0
            UpdateTaskScreen(idTask, viewModel, navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NoteHaieTheme {
        HomeScreenContent(ExempleTask.tasks, {_, _ ->  }, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun NewTaskScreenPreview() {
    NoteHaieTheme {
        NewTaskScreenContent({}, {true}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateTaskScreenPreview() {
    NoteHaieTheme {
        UpdateTaskScreenContent(ExempleTask.tasks[0], {true}, {}, {})
    }
}

@Composable
fun ParameterScreen() { //navController: NavHostController
    // TODO("Not yet implemented")
}