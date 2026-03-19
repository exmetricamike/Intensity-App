package com.intensityrecords.app.core.presentation

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecords.app.core.domain.AppDimens
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.montserrat_light
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import org.jetbrains.compose.resources.Font


val Inter
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.montserrat_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resource = Res.font.montserrat_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.montserrat_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.montserrat_regular,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.montserrat_light,
            weight = FontWeight.Light
        ),
    )

val Typography: Typography
    @Composable get() = Typography(

        // This is used in AppHeader.
        headlineMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = 1.sp,
            color = Color.White
        ),

        // This is used in All Card title.
        bodyLarge = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 0.1.sp,
            color = Color.White
        ),

        // This is used in every TAG's like "live"
        labelSmall = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            letterSpacing = 0.1.sp,
            color = Color.Black
        ),

        // This is used in All button text
        labelMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 0.1.sp,
            color = Color.White
        ),

        // This is used in All Card Captions(Title below)
        bodyMedium = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            letterSpacing = 0.1.sp,
            color = Color.White
        ),

        // This is used in All Card Chip type captions(Bottom side captions.)
        displaySmall = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp,
            color = Color.White
        ),

        // This is used in All screen main Title.
        titleLarge = TextStyle(
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 1.5.sp,
            color = Color.White
        ),
    )


val LocalAppDimens = staticCompositionLocalOf { CompactDimens }


// This is used for MOBILE SCREEN
val CompactDimens = AppDimens(
    paddingSmall = 4.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp,
    borderNormal = 1.dp,
    borderActive = 3.dp,
    cornerRadius = 16.dp,

    videoCardHeight = 265.dp,
    buttonHeight = 40.dp,

    titleLarge = 20.sp,
    bodyMedium = 14.sp,
    buttonText = 14.sp,

    horizontalContentPadding = 16.dp,
    verticalContentPadding = 24.dp,
    elevationStateActive = 12.dp,
    elevationStateNormal = 4.dp,
    borderWidthActive = 2.dp,
    borderWidthNormal = 1.dp,
    cardHeight = 120.dp,
    cardCornerRadius = 16.dp,
    cardTitle = 16.sp,
    live = 10.sp,
    sessionTitle = 14.sp,
    introVideo = 14.sp,
    rePlayButton = 14.sp,
    agendaButton = 14.sp,
    largePlayButton = 14.sp,
)

// This is used for TV Screen
val ExpandedDimens = AppDimens(
    paddingSmall = 8.dp,
    paddingMedium = 24.dp,
    paddingLarge = 32.dp,
    borderNormal = 2.dp,
    borderActive = 4.dp,
    cornerRadius = 24.dp,

    videoCardHeight = 280.dp,
    buttonHeight = 48.dp,

    titleLarge = 40.sp,
    bodyMedium = 22.sp,
    buttonText = 18.sp,

    horizontalContentPadding = 58.dp,
    verticalContentPadding = 16.dp,
    elevationStateActive = 12.dp,
    elevationStateNormal = 4.dp,
    borderWidthActive = 3.dp,
    borderWidthNormal = 1.dp,
    cardHeight = 170.dp,
    cardCornerRadius = 16.dp,
    cardTitle = 18.sp,
    live = 10.sp,
    sessionTitle = 20.sp,
    introVideo = 16.sp,
    rePlayButton = 18.sp,
    agendaButton = 18.sp,
    largePlayButton = 18.sp,
)