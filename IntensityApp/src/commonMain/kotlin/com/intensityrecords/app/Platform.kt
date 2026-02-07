package com.intensityrecord

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform