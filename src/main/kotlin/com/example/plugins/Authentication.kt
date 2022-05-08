package com.example.plugins

import com.example.domain.model.Endpoint
import com.example.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.authentication() {
    install(Authentication) {
        session<UserSession>(
            name = "authentication_session",
            configure = {
                validate { session ->
                    session
                }
                challenge {
                    call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
                }
            }
        )
    }
}