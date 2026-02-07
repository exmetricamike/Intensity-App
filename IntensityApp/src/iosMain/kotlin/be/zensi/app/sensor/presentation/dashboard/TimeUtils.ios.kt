package com.intensityrecord.sensor.presentation.dashboard

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long =
    (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun formatCurrentTime(): String {
    val formatter = NSDateFormatter()
    formatter.dateFormat = "HH:mm:ss"
    return formatter.stringFromDate(NSDate())
}
