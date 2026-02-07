package com.intensityrecord.sensor.presentation.calibrate

sealed interface CalibrateAction {
    data object Next : CalibrateAction
    data object Previous : CalibrateAction
    data object Cancel : CalibrateAction
    data object DoCalibrate : CalibrateAction
    data object Finish : CalibrateAction
}
