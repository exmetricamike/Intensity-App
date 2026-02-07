package com.intensityrecord.sensor.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intensityrecord.sensor.presentation.components.AlarmDialog
import com.intensityrecord.sensor.presentation.components.DisconnectedDialog
import com.intensityrecord.sensor.presentation.components.PinDialog
import com.intensityrecord.sensor.presentation.components.SensorCard
import com.intensityrecord.sensor.presentation.components.StatusBottomBar
import com.intensityrecord.sensor.presentation.components.TopBar

@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopBar(
            userName = state.userName,
            logoUrl = state.logoUrl,
            onSettingsClick = { onAction(DashboardAction.OnSettingsClick) }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            if (state.isLoading && state.sensorList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.sensorList.isEmpty()) {
                Text(
                    text = "No sensors found",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(state.columns),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.sensorList,
                        key = { it.padId }
                    ) { sensor ->
                        SensorCard(
                            sensor = sensor,
                            buttonStatusColors = state.buttonStatusColors,
                            onClick = { onAction(DashboardAction.OnSensorClick(sensor.padId)) },
                            onLongClick = { onAction(DashboardAction.OnSensorLongClick(sensor.padId)) }
                        )
                    }
                }
            }
        }

        StatusBottomBar(
            lastUpdateTime = state.lastUpdateTime,
            connectionLost = state.connectionLost
        )
    }

    // Dialogs
    if (state.showAlarmDialog) {
        AlarmDialog(
            sensorName = state.alarmSensorName,
            onDismiss = { onAction(DashboardAction.DismissAlarmDialog) },
            onSnooze = { onAction(DashboardAction.SnoozeAlarm) }
        )
    }

    if (state.showDisconnectedDialog) {
        DisconnectedDialog(
            onDismiss = { onAction(DashboardAction.DismissDisconnectedDialog) }
        )
    }

    if (state.showPinDialog) {
        PinDialog(
            onDismiss = { onAction(DashboardAction.DismissPinDialog) },
            onPinEntered = { pin -> onAction(DashboardAction.OnPinEntered(pin)) }
        )
    }
}
