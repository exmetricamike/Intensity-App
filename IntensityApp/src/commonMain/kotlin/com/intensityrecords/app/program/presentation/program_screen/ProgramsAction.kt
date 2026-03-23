package com.intensityrecords.app.program.presentation.program_screen

import com.intensityrecords.app.program.domain.ProgramSection

sealed interface ProgramsAction {
    data class OnProgramClick(val program: ProgramSection) : ProgramsAction
}
