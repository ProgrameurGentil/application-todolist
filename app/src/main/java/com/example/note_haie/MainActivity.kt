package com.example.note_haie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note_haie.model.ExempleTask
import com.example.note_haie.ui.screens.home.HomeScreen
import com.example.note_haie.ui.screens.newtask.NewTaskScreen
import com.example.note_haie.ui.theme.NoteHaieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteHaieTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
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
            HomeScreen(ExempleTask.tasks, navController)
        }
        composable("new-task") {
            NewTaskScreen(navController)
        }
        composable("parameter") {
            ParameterScreen()
        }
        composable("update-task") {
            UpdateTaskScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NoteHaieTheme {
        HomeScreen(ExempleTask.tasks, rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NewTaskScreenPreview() {
    NoteHaieTheme {
        NewTaskScreen(rememberNavController())
    }
}

@Composable
fun ParameterScreen() { //navController: NavHostController
    TODO("Not yet implemented")
}

@Composable
fun UpdateTaskScreen() { //navController: NavHostController
    TODO("Not yet implemented")
}