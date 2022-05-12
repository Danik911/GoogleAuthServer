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
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*

fun Routing.deleteUser(
    app: Application,
    userDataSource: UserDataSource
) {
    authenticate("authentication_session") {
        delete(Endpoint.DeleteUser.route) {
            val session = call.principal<UserSession>()

            if (session != null) {
                try {
                    call.sessions.clear<UserSession>()
                    deleteUser(
                        app = app,
                        userDataSource = userDataSource,
                        userId = session.id
                    )
                } catch (e: Exception) {
                    app.log.info("FAILED TO DELETE USER")
                    call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
                }
            } else{
                app.log.info("INVALID SESSION")
                call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
            }

        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.deleteUser(
    app: Application,
    userDataSource: UserDataSource,
    userId: String
) {
    val response = userDataSource.deleteUser(userId = userId)

    if (response) {
        call.respond(
            message = ApiResponse(
                success = true,
                message = "USER HAS BEEN DELETED"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        call.respond(
            message = ApiResponse(
                success = false
            ),
            status = HttpStatusCode.BadRequest
        )
    }
}