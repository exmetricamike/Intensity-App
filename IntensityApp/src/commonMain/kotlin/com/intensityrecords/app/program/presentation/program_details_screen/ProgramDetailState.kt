package com.intensityrecords.app.program.presentation.program_details_screen

import com.intensityrecords.app.program.domain.ProgramCollectionDetail

data class ProgramDetailState(
    val isLoading: Boolean = false,
    val collection: ProgramCollectionDetail? = null,
    val error: String? = null
)
