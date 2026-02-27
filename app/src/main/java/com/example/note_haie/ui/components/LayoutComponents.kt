package com.example.note_haie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.note_haie.R
import com.example.note_haie.ui.theme.DarkBlue

@Composable
fun HeaderView() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(DarkBlue)) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_note_haie),
                contentDescription = "Logo note haie",
                modifier = Modifier.size(40.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.icon_parametres),
                contentDescription = "Icon des parametres",
                modifier = Modifier.size(32.dp)
            )
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
