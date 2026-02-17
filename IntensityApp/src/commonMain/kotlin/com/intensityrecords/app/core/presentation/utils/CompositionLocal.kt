package com.intensityrecords.app.core.presentation.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecords.app.core.domain.AppDimens

val CompactDimens = AppDimens(
    paddingSmall = 4.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp,
    borderNormal = 1.dp,
    borderActive = 3.dp,
    cornerRadius = 16.dp,

    videoCardHeight = 255.dp,
    buttonHeight = 40.dp,

    titleLarge = 20.sp,
    bodyMedium = 14.sp,
    buttonText = 12.sp,

    horizontalContentPadding = 16.dp,
    verticalContentPadding = 24.dp,
    elevationStateActive = 12.dp,
    elevationStateNormal = 4.dp,
    borderWidthActive = 2.dp,
    borderWidthNormal = 1.dp,
    cardHeight = 120.dp,
    cardCornerRadius = 16.dp,
    title = 16.sp,
    live = 10.sp,
    sessionTitle = 14.sp,
)

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
    title = 18.sp,
    live = 10.sp,
    sessionTitle = 20.sp
)


val LocalAppDimens = staticCompositionLocalOf { CompactDimens }