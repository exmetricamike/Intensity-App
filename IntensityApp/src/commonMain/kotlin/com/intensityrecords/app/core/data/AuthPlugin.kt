package com.plcoding.auth.data.repository


import com.intensityrecord.auth.TokenStorage
import io.ktor.client.*
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.AttributeKey
import kotlinx.coroutines.sync.Mutex

/**
 * Ktor client plugin for automatic JWT authentication and refresh.
 *
 * Client-side plugin for KMP, designed to work with a JWT backend.
 * Responsibilities:
 * 1 Attach the current access token to all requests.
 * 2 Detect 401 Unauthorized responses and refresh tokens via `/auth/refresh`.
 * 3 Retry the original request once with the new token.
 *
 * @param tokenStorage Provides access to saved tokens and allows saving refreshed tokens.
 * @param refreshEndpoint The backend endpoint used to refresh the access token.
 */
class AuthPlugin private constructor(
    private val tokenStorage: TokenStorage,
    private val refreshEndpoint: String
) {

    /** Prevent concurrent token refreshes */
    private val refreshMutex = Mutex()

    /**
     * Plugin configuration class.
     */
    class Config {
        /** Token storage to retrieve and save access/refresh tokens */
        lateinit var tokenStorage: TokenStorage

        /** Endpoint to call when refreshing tokens */
        var refreshEndpoint: String = "/auth/refresh"
    }

    companion object Plugin : HttpClientPlugin<Config, AuthPlugin> {
        override val key: AttributeKey<AuthPlugin> = AttributeKey("AuthPlugin")

        /**
         * Prepare plugin instance from configuration
         */
        override fun prepare(block: Config.() -> Unit): AuthPlugin {
            val config = Config().apply(block)
            return AuthPlugin(config.tokenStorage, config.refreshEndpoint)
        }

        /**
         * Install the plugin in the HttpClient
         *
         * @param plugin The AuthPlugin instance
         * @param scope The HttpClient to install into
         */
        override fun install(plugin: AuthPlugin, scope: HttpClient) {

            // ------------------------------------------------
            // 1 Attach access token to all outgoing requests
            // ------------------------------------------------
            scope.sendPipeline.intercept(HttpSendPipeline.State) {
                val accessToken = plugin.tokenStorage.getAccessToken()
                if (!accessToken.isNullOrBlank()) {
                    context.headers.append(HttpHeaders.Authorization, "Token $accessToken")
                }
            }

            // ----------------------------
            // 2️⃣ Intercept all responses and handle 401 Unauthorized
            // ----------------------------





        }
    }


}
