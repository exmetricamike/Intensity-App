package com.intensityrecords.app.core.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecords.app.core.data.HotelSession
import org.koin.compose.koinInject

@Composable
fun HotelHeader(
    isWideScreen: Boolean,
    onLogOut: () -> Unit,
    hotelSession: HotelSession = koinInject()
) {
    val theme by hotelSession.theme.collectAsStateWithLifecycle()

    val headerTextColor = theme.headerTextColor
        ?.let { parseHexColor(it) }
        ?: TextWhite

    val logoutIconSize = if (isWideScreen) 30.dp else 20.dp
    val startPadding = if (isWideScreen) 32.dp else 16.dp

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 0.dp,
        label = "iconBorder"
    )

    Box(modifier = Modifier.fillMaxWidth()) {

        // Logout button — always visible
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
                    color = if (isFocused) Color.White.copy(alpha = 0.15f)
                    else Color.White.copy(alpha = 0.08f),
                    shape = CircleShape
                )
                .border(width = borderWidth, color = PrimaryAccent, shape = CircleShape)
                .clickable(interactionSource = interactionSource, indication = null) {
                    onLogOut()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Logout,
                contentDescription = "Log out",
                tint = if (isFocused) PrimaryAccent else Color.White,
                modifier = Modifier.size(logoutIconSize)
            )
        }

        // Hotel branding — matches original AppHeader styling;
        // colours will be wired from theme in a later step
        if (theme.showHeader) Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (theme.showLogo) {
                val logoUrl = theme.hotelLogo
                if (logoUrl != null) {
                    AsyncImage(
                        model = logoUrl,
                        contentDescription = "Hotel logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .then(
                                if (isWideScreen)
                                    Modifier.width(320.dp).heightIn(max = 110.dp)
                                else
                                    Modifier.fillMaxWidth(0.9f).heightIn(max = 140.dp)
                            )
                    )
                }
//                else {
//                    Icon(
//                        imageVector = Icons.Rounded.Spa,
//                        contentDescription = "Hotel logo",
//                        tint = headerTextColor,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val tagline = theme.hotelTagline
                if (theme.showTagline && tagline != null) {
                    Text(
                        text = theme.hotelName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = headerTextColor.copy(alpha = 0.6f)
                        )
                    )
                    Text(
                        text = tagline,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = headerTextColor
                        )
                    )
                }
                if (!isWideScreen) Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}

/** Parses a hex color string such as "#FF5733" or "FF5733" into a [Color]. Returns null on failure. */
fun parseHexColor(hex: String): Color? = try {
    val cleaned = hex.trimStart('#')
    val argb = when (cleaned.length) {
        6 -> "FF$cleaned".toLong(16)
        8 -> cleaned.toLong(16)
        else -> null
    } ?: return null
    Color(argb.toInt())
} catch (_: NumberFormatException) {
    null
}
