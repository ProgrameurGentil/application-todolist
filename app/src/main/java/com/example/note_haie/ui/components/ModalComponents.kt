package com.example.note_haie.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.note_haie.R
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
