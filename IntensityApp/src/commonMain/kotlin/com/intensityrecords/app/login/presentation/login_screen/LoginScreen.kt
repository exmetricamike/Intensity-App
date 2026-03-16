package com.intensityrecords.app.login.presentation.login_screen

//@Composable
//fun LoginScreenRoot(
//    viewModel: LoginScreenViewModel = koinViewModel(),
//    onLoginSuccess: () -> Unit
//) {
//    val state = viewModel.state.collectAsStateWithLifecycle().value
//
//    // Navigate when login succeeds
//    LaunchedEffect(state.isLoginSuccessful) {
//        if (state.isLoginSuccessful) {
//            onLoginSuccess()
//        }
//    }
//
//    LoginScreen(
//        state = state,
//        onAction = viewModel::onAction
//    )
//}
//
//@Composable
//private fun LoginScreen(
//    state: LoginState,
//    onAction: (LoginAction) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Sign In", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        OutlinedTextField(
//            value = state.usernameText,
//            onValueChange = { onAction(LoginAction.OnUsernameChange(it)) },
//            label = { Text("Username") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = state.passwordText,
//            onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
//            label = { Text("Password") },
//            visualTransformation = PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        if (state.errorMessage != null) {
//            Text(
//                text = state.errorMessage,
//                color = MaterialTheme.colorScheme.error
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//
//        Button(
//            onClick = { onAction(LoginAction.OnLoginClick) },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = !state.isLoading
//        ) {
//            if (state.isLoading) {
//                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
//            } else {
//                Text("Login")
//            }
//        }
//    }
//}

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.LocalAppDimens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginScreenViewModel = koinViewModel(),
    isWideScreen: Boolean, // Passed from App.kt
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Navigate when login succeeds
    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction,
        isWideScreen = isWideScreen
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    isWideScreen: Boolean
) {
    val focusManager = LocalFocusManager.current
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val buttonFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        usernameFocusRequester.requestFocus()
    }

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient),
            contentAlignment = Alignment.Center // Centers the login form vertically and horizontally
        ) {
            val dimens = LocalAppDimens.current

            // Restrict width on TV so text fields aren't massively wide
            val formWidth = if (isWideScreen) 0.5f else 0.9f

            Column(
                modifier = Modifier
                    .fillMaxWidth(formWidth)
                    .padding(horizontal = dimens.horizontalContentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = if (isWideScreen) 32.sp else 28.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.LightGray,
                        fontSize = if (isWideScreen) 18.sp else 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(if (isWideScreen) 48.dp else 32.dp))

                // Custom Colors so text is visible on the DarkGradient
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White
                )

                OutlinedTextField(
                    value = state.usernameText,
                    onValueChange = { onAction(LoginAction.OnUsernameChange(it)) },
                    label = { Text("Username") },
                    colors = textFieldColors,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(usernameFocusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.passwordText,
                    onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            buttonFocusRequester.requestFocus() // Move focus to button for TV D-Pad
                        }
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onAction(LoginAction.OnLoginClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isWideScreen) 56.dp else 50.dp)
                        .focusRequester(buttonFocusRequester),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 3.dp
                        )
                    } else {
                        Text(
                            text = "LOGIN",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = if (isWideScreen) 18.sp else 16.sp
                            )
                        )
                    }
                }
            }
        }
    }
}