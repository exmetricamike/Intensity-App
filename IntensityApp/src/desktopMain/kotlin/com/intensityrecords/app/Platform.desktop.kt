package com.intensityrecords.app

import io.ktor.util.Platform

actual fun getPlatform(): Platform {
  return  Platform()
}