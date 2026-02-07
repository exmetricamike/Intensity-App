package com.intensityrecord.sensor.presentation.demo

data class DemoState(
    val currentStep: Int = 0,
    val steps: List<DemoStep> = defaultSteps
) {
    val currentStepData: DemoStep get() = steps.getOrElse(currentStep) { steps.first() }
    val canGoNext: Boolean get() = currentStep < steps.size - 1
    val canGoPrevious: Boolean get() = currentStep > 0
    val isLastStep: Boolean get() = currentStep == steps.size - 1
}

data class DemoStep(
    val title: String,
    val description: String
)

val defaultSteps = listOf(
    DemoStep(
        title = "Welcome to Zensi",
        description = "Zensi is a smart bed-sensor monitoring system that helps caregivers keep track of bed occupancy in real-time."
    ),
    DemoStep(
        title = "Sensor Dashboard",
        description = "The main screen shows a grid of sensor cards. Each card represents a bed sensor with color-coded status: green for occupied, gray for standby, and alerts in red/orange."
    ),
    DemoStep(
        title = "Real-time Updates",
        description = "The dashboard automatically polls for sensor status updates every few seconds. You can see the last update time at the bottom of the screen."
    ),
    DemoStep(
        title = "Sensor Settings",
        description = "Long-press any sensor card to access its settings. You can rename sensors, configure time slots, set alert thresholds, and calibrate the sensor."
    ),
    DemoStep(
        title = "Request a Demo",
        description = "Interested in trying Zensi for your facility? Contact us to set up a personalized demo with real sensor data."
    )
)
