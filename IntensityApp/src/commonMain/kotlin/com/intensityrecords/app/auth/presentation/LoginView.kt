package com.intensityrecord.auth.presentation

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.intensityrecord.core.presentation.Icons.Visibility
import com.intensityrecord.core.presentation.Icons.Visibility_off
import com.intensityrecord.core.presentation.ZensiPrimary
import com.intensityrecord.core.presentation.ZensiPrimaryDark

@Composable
fun LoginViewRoot(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onDemoClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                LoginNavigationEvents.Authenticated -> onLoginSuccess()
                LoginNavigationEvents.NavigateToDemo -> onDemoClick()
            }
        }
    }

    LoginScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun LoginScreen(
    state: LoginState = LoginState(),
    onEvent: (LoginEventAction) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ZensiPrimary,
                        ZensiPrimaryDark
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Zensi",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sensor Monitoring",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { onEvent(LoginEventAction.UserNameChanged(it)) },
                    label = { Text("Username") },
                    placeholder = { Text("Enter your username") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = state.usernameError != null
                )
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedVisibility(visible = state.usernameError != null) {
                    Text(
                        text = state.usernameError.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onEvent(LoginEventAction.PasswordChanged(it)) },
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { onEvent(LoginEventAction.TogglePasswordVisibility) }) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) Visibility_off else Visibility,
                                contentDescription = "Password Visibility Toggle"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = state.passwordError != null
                )
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedVisibility(visible = state.passwordError != null) {
                    Text(
                        text = state.passwordError.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            AnimatedVisibility(visible = state.loginError != null) {
                Text(
                    text = state.loginError.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = { onEvent(LoginEventAction.LoginClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = ZensiPrimary
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = ZensiPrimary
                    )
                } else {
                    Text("Login", fontWeight = FontWeight.Bold)
                }
            }

            if (state.showDemoLink) {
                TextButton(onClick = { onEvent(LoginEventAction.DemoLinkClicked) }) {
                    Text(
                        "Try a demo",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "v${state.versionName}",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
    }
}
