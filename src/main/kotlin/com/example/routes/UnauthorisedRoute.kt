package com.example.routes

import com.example.domain.model.Endpoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.unauthorisedUser() {
    get(Endpoint.UnAuthorisedAccess.route){
        call.respond(
            message = "unauthorised_user",
            status = HttpStatusCode.Unauthorized
        )
    }
}