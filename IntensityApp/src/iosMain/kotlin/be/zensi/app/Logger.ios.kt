package com.intensityrecord

actual fun logDebug(tag: String, message: String) {
    println("[$tag] $message")
}