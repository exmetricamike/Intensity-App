package com.intensityrecord.sensor.presentation.calibrate

data class CalibrateState(
    val sensorPadId: String = "",
    val currentStep: Int = 0,
    val totalSteps: Int = 5,
    val isCalibrating: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val stepInstructions: List<String>
        get() = listOf(
            "Remove all weight from the sensor pad.",
            "Make sure the bed is empty and the sheets are flat.",
            "Ensure nothing is pressing on the sensor.",
            "Keep the sensor area clear for calibration.",
            "Press 'Calibrate' to start the calibration process."
        )

    val currentInstruction: String
        get() = stepInstructions.getOrElse(currentStep) { "" }

    val canGoNext: Boolean get() = currentStep < totalSteps - 1
    val canGoPrevious: Boolean get() = currentStep > 0
    val isLastStep: Boolean get() = currentStep == totalSteps - 1
}
