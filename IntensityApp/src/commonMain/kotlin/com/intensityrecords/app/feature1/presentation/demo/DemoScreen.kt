package com.intensityrecord.sensor.presentation.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.intensityrecord.core.presentation.ZensiPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen(
    state: DemoState,
    onAction: (DemoAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demo") },
                navigationIcon = {
                    IconButton(onClick = { onAction(DemoAction.OnBackClick) }) {
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Step indicator dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    for (i in state.steps.indices) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (i == state.currentStep) ZensiPrimary
                                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = state.currentStepData.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = ZensiPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.currentStepData.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.canGoPrevious) {
                    OutlinedButton(
                        onClick = { onAction(DemoAction.Previous) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Previous")
                    }
                }

                if (state.isLastStep) {
                    Button(
                        onClick = { onAction(DemoAction.RequestDemo) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Request Demo")
                    }
                } else {
                    Button(
                        onClick = { onAction(DemoAction.Next) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}
