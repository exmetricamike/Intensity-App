package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import com.intensityrecords.app.workouts.domain.Session

sealed interface WorkOutsDetailAction {
    data object OnBackClick : WorkOutsDetailAction
    data object OnCoachChooseClick : WorkOutsDetailAction
    data class OnSessionClick(val session: Session) : WorkOutsDetailAction
}