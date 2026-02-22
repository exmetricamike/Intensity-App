package com.intensityrecords.app.live.presentation.live_screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import org.jetbrains.compose.resources.Font

@Composable
fun LiveTagBadge() {
    Box(
        modifier = Modifier
            .border(1.dp, PrimaryAccent, RoundedCornerShape(50))
            .padding(horizontal = 24.dp, vertical = 6.dp)
    ) {
        Text(
            text = "LIVE",
            style = TextStyle(
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.montserrat_bold))
            )
        )
    }
}