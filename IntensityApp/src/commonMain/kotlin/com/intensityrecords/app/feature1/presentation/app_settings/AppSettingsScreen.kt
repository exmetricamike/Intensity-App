package com.intensityrecord.sensor.presentation.app_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(
    state: AppSettingsState,
    onAction: (AppSettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Settings") },
                navigationIcon = {
                    IconButton(onClick = { onAction(AppSettingsAction.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Number of columns
            Column {
                Text(
                    text = "Grid Columns: ${state.numColumns}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = state.numColumns.toFloat(),
                    onValueChange = {
                        onAction(AppSettingsAction.ChangeColumns(it.roundToInt()))
                    },
                    valueRange = 1f..5f,
                    steps = 3
                )
            }

            // Sound
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sound", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = state.soundEnabled,
                    onCheckedChange = { onAction(AppSettingsAction.ToggleSound) }
                )
            }

            // Vibrate
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Vibrate", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = state.vibrateEnabled,
                    onCheckedChange = { onAction(AppSettingsAction.ToggleVibrate) }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Logout
            Button(
                onClick = { onAction(AppSettingsAction.Logout) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("Logout", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
