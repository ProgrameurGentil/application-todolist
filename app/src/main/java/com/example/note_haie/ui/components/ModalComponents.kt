package com.example.note_haie.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.note_haie.R
import com.example.note_haie.model.jokes
import com.example.note_haie.ui.theme.NoteHaieTheme

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
                onClick = { onDismiss() }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {}
    )
}

@Composable
fun ConfirmModal(title: String, text: String, onDismiss: () -> Unit, onDismissTrue: () -> Unit, onDismissFalse: () -> Unit) {
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
                onClick = {
                    onDismissTrue()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissFalse()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.annuler))
            }
        }
    )
}

@Composable
fun JokeModal(onDismiss: () -> Unit) {
    val joke = remember { jokes[jokes.indices.random()] }
    var showResponse by remember { mutableStateOf(false) }

    if (!showResponse) {
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.petit_blague))
            },
            text = {
                Text(text = joke.question)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        showResponse = true
                    }
                ) {
                    Text(stringResource(R.string.reponse))
                }
            },
            dismissButton = {}
        )
    } else {
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.reponse_blague))
            },
            text = {
                Column{
                    Text(text = joke.response)
                    Text(text = stringResource(R.string.auteur_blague, joke.author), fontStyle = FontStyle.Italic)
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.retour_a_app))
                }
            },
            dismissButton = {}
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
