package com.intensityrecords.app.core.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecords.app.app.Route
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.app_header
import intensityrecordapp.intensityapp.generated.resources.app_header_1
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppHeader(
    isWideScreen: Boolean,
    onLogOut: () -> Unit,
    isLoginScreen: Boolean
) {

    val logoutIconSize = if (isWideScreen) 30.dp else 20.dp
    val startPadding = if (isWideScreen) 32.dp else 16.dp

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.2f else 1f,
        label = "iconScale"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 0.dp,
        label = "iconBorder"
    )

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isLoginScreen) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = startPadding)
                    .size(if (isWideScreen) 50.dp else 32.dp)
                    .shadow(
                        elevation = if (isFocused) 2.dp else 0.dp,
                        shape = CircleShape,
                        ambientColor = PrimaryAccent,
                        spotColor = PrimaryAccent
                    )
                    .background(
                        color = if (isFocused)
                            Color.White.copy(alpha = 0.15f)
                        else
                            Color.White.copy(alpha = 0.08f),
                        shape = CircleShape
                    )
                    .border(
                        width = borderWidth,
                        color = PrimaryAccent,
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onLogOut()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = "Log out",
                    tint = if (isFocused) PrimaryAccent else Color.White,
                    modifier = Modifier.size(logoutIconSize).align(Alignment.Center)
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Spa,
                contentDescription = "Logo",
                tint = TextWhite,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(Res.string.app_header),
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.Gray),
                )
                Text(
                    text = stringResource(Res.string.app_header_1),
                    style = MaterialTheme.typography.headlineMedium,
                )
                if (!isWideScreen)
                    Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}
