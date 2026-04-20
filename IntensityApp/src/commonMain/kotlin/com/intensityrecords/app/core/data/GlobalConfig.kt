package com.intensityrecords.app.core.data



object GlobalConfig {
    const val API_VERSION = "v1"
    const val DEBUG_PRINT_API_RESPONSE = true
    const val IGNORE_SSL_ERRORS = false

    /*
    * NOTE FOR LOCAL DEVELOPMENT with HTTP (not HTTPS):
    * 1. Set API_ENDPOINT to your local IP (e.g. http://192.168.1.16:8000/)
    * 2. Android: verified `android:usesCleartextTraffic="true"` is in AndroidManifest.xml
    * 3. iOS: verified `NSAppTransportSecurity` -> `NSAllowsArbitraryLoads` is true in Info.plist
    */

    // backend servers:
    // main:  https://intensityapi.exmetrica.be/
    // local: http://192.168.1.16:8000/
    const val API_ENDPOINT = "http://192.168.1.16:8000/"
    //const val API_ENDPOINT = "https://intensityapi.exmetrica.be/"

}
