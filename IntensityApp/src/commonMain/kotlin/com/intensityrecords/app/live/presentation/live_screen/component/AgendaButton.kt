package com.intensityrecords.app.live.presentation.live_screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecords.app.core.presentation.cardTitle
import org.jetbrains.compose.resources.Font

@Composable
fun AgendaButton(onClick: () -> Unit, isWideScreen: Boolean) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val textColor = if (isFocused) PrimaryAccent else Color.White
    val borderBrush = if (isFocused) SolidColor(PrimaryAccent) else GlowBorderBrush
    val containerColor = if (isFocused) PrimaryAccent.copy(alpha = 0.1f) else Color.Transparent


    Row(
        modifier = Modifier
            .width(if (isWideScreen) 200.dp else 150.dp)
            .height(56.dp)
            .clip(CircleShape)
            .background(containerColor)
            .border(BorderStroke(2.dp, borderBrush), CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
//        Icon(Icons.Filled.PlayCircle, contentDescription = null, tint = PrimaryAccent)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "AGENDA",
            style = cardTitle.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = FontFamily(Font(Res.font.montserrat_bold))
            )
        )
    }
}