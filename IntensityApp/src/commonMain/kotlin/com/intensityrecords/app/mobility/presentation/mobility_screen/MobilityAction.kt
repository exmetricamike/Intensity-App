package com.intensityrecords.app.mobility.presentation.mobility_screen

import com.intensityrecords.app.mobility.domain.MobilityItems

sealed interface MobilityAction {

    data class OnClick(val mobilityData: MobilityItems) : MobilityAction

    object LoadMobility : MobilityAction

}