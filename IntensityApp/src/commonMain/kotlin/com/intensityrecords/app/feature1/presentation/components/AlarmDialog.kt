package com.intensityrecord.sensor.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlarmDialog(
    sensorName: String,
    onDismiss: () -> Unit,
    onSnooze: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Alert") },
        text = { Text("Sensor \"$sensorName\" has triggered an alert.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onSnooze) {
                Text("Snooze")
            }
        }
    )
}
