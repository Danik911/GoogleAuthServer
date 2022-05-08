package com.example.routes

import com.example.domain.model.Endpoint
import com.example.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Routing.tokenVerification() {
    post(Endpoint.TokenVerification.route) {
        call.sessions.set(UserSession(name = "Daniil", id = "123"))
        call.respondRedirect(Endpoint.AuthorisedAccess.route)
    }
}