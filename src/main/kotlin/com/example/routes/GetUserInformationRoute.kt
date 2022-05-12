package com.example.routes

import com.example.domain.model.ApiResponse
import com.example.domain.model.Endpoint
import com.example.domain.model.UserSession
import com.example.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.getUserInformationRoute(app: Application, userDataSource: UserDataSource) {
    authenticate("authentication_session") {
        get(Endpoint.GetUser.route) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.info("NO SESSION FOUND")
                call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
            } else {
                try {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            user = userDataSource.getUserInfo(userId = userSession.id),

                            ),
                        status = HttpStatusCode.OK
                    )

                } catch (e: java.lang.Exception) {
                    app.log.info("NO USER WAS FOUND")
                }
            }
        }
    }
}