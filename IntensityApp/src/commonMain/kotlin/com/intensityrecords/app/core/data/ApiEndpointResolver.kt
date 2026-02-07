package com.intensityrecord.core.data

object ApiEndpointResolver {

    const val API_ENDPOINT = "https://app.zensi.be/"
    const val API_ENDPOINT_ANIMA = "https://anima.zensi.be/"
    const val API_VERSION = "v3"

    private val animaClients = listOf(
        "Alegria", "Aquamarijn", "AuPrivilege", "ChateauAwans",
        "ComtesMean", "DeToekomst", "Krekelmuyter", "Kristallijn",
        "Kruyenberg", "Neerveld", "Nuance", "Zevenbronnen", "Schelde", "Ravelijn"
    )

    private var resolvedEndpoint: String? = null

    fun resolveForLogin(username: String): String {
        val endpoint = if (animaClients.contains(username) || username.startsWith("Anima")) {
            API_ENDPOINT_ANIMA
        } else {
            API_ENDPOINT
        }
        resolvedEndpoint = endpoint
        return endpoint
    }

    fun getBaseUrl(): String {
        return resolvedEndpoint ?: API_ENDPOINT
    }

    fun getVersionedUrl(endpoint: String): String {
        return "${getBaseUrl()}api/$API_VERSION/$endpoint/"
    }

    fun getV2Url(endpoint: String): String {
        return "${getBaseUrl()}api/v2/$endpoint/"
    }

    fun clear() {
        resolvedEndpoint = null
    }
}
