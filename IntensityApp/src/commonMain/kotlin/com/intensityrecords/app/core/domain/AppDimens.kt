package com.intensityrecords.app.core.domain

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class AppDimens(

    val paddingSmall: Dp,
    val paddingMedium: Dp,

    // GLOBAL USE
    val horizontalContentPadding: Dp,
    val verticalContentPadding: Dp,
    val elevationStateActive: Dp,
    val elevationStateNormal: Dp,
    val borderWidthActive: Dp,
    val borderWidthNormal: Dp,
    val cardCornerRadius: Dp,
    val cardTitle: TextUnit,
    val live: TextUnit,
    val introVideo: TextUnit,
    val rePlayButton: TextUnit,
    val agendaButton: TextUnit,
    val largePlayButton: TextUnit,

    // HOME SCREEN VIDEO OF THE DAY CARD
    val videoCardHeight: Dp,
    val borderNormal: Dp,
    val borderActive: Dp,
    val cornerRadius: Dp,
    val paddingLarge: Dp,
    val titleLarge: TextUnit,
    val bodyMedium: TextUnit,
    val buttonText: TextUnit,
    val buttonHeight: Dp,

    // HOME SCREEN CARD
    val cardHeight: Dp,

    // SESSION CARD
    val sessionTitle: TextUnit,

    )