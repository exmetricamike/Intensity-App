package com.intensityrecord.sensor.presentation.sensor_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorSettingsScreen(
    state: SensorSettingsState,
    onAction: (SensorSettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sensor Settings") },
                navigationIcon = {
                    IconButton(onClick = { onAction(SensorSettingsAction.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            )
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Display Name
                OutlinedTextField(
                    value = state.displayName,
                    onValueChange = { onAction(SensorSettingsAction.RenameDisplay(it)) },
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Monitor On/Off
                SettingsSwitch(
                    label = "Monitoring Active",
                    checked = state.monitorOn,
                    onCheckedChange = { onAction(SensorSettingsAction.ToggleMonitor) }
                )

                // Time Slots
                Text("Time Slots", style = MaterialTheme.typography.titleMedium)
                for (slot in 1..5) {
                    val isOn = when (slot) {
                        1 -> state.timeSlot1
                        2 -> state.timeSlot2
                        3 -> state.timeSlot3
                        4 -> state.timeSlot4
                        5 -> state.timeSlot5
                        else -> false
                    }
                    val label = state.timeslotLabels.getOrElse(slot - 1) { "Slot $slot" }
                    SettingsSwitch(
                        label = label,
                        checked = isOn,
                        onCheckedChange = { onAction(SensorSettingsAction.ToggleTimeSlot(slot)) }
                    )
                }

                // Alert Time
                if (state.alertTimeLabels.isNotEmpty()) {
                    Text("Alert Threshold", style = MaterialTheme.typography.titleMedium)
                    AlertTimeSelector(
                        labels = state.alertTimeLabels,
                        selectedIndex = state.alertTimeIdx,
                        onSelect = { onAction(SensorSettingsAction.ChangeAlertTime(it)) }
                    )
                }

                // Repeat alarm
                SettingsSwitch(
                    label = "Repeat alarm until seen",
                    checked = state.repeatAlarmUntilSeen,
                    onCheckedChange = { onAction(SensorSettingsAction.ToggleRepeatAlarm) }
                )

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.chartEnabled) {
                        TextButton(onClick = { onAction(SensorSettingsAction.OpenChart) }) {
                            Text("View Chart")
                        }
                    }
                    if (state.calibrateEnabled) {
                        TextButton(onClick = { onAction(SensorSettingsAction.OpenCalibrate) }) {
                            Text("Calibrate")
                        }
                    }
                }

                // Error
                if (state.error != null) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Save Button
                Button(
                    onClick = { onAction(SensorSettingsAction.Save) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.padding(4.dp))
                    } else {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = { onCheckedChange() })
    }
}

@Composable
private fun AlertTimeSelector(
    labels: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        labels.forEachIndexed { index, label ->
            Button(
                onClick = { onSelect(index) },
                modifier = Modifier.weight(1f),
                enabled = index != selectedIndex
            ) {
                Text(label, maxLines = 1)
            }
        }
    }
}
