package com.intensityrecords.app.program.presentation.program_screen

import com.intensityrecords.app.program.domain.ProgramSection

data class ProgramsState(
    val isLoading: Boolean = false,
    val sections: List<ProgramSection> = emptyList(),
    val error: String? = null
)
