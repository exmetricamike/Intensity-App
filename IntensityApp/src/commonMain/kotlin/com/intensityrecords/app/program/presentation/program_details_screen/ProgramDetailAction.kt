package com.intensityrecords.app.program.presentation.program_details_screen

sealed interface ProgramDetailAction {
    data object OnBackClick : ProgramDetailAction
}
