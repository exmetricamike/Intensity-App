package com.intensityrecords.app.mobility.presentation.mobility_screen

import com.intensityrecords.app.mobility.domain.MobilityItems

data class MobilityState(

    val mobilityData: List<MobilityItems> = emptyList(),

    val loading: Boolean = false,

    val error: String? = null

)