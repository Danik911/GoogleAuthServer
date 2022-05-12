package com.example.routes

import com.example.domain.model.ApiResponse
import com.example.domain.model.Endpoint
import com.example.domain.model.UserSession
import com.example.domain.model.UserUpdate
import com.example.domain.repository.UserDataSource
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Routing.updateUserInfo(app: Application, userDataSource: UserDataSource) {

    authenticate("authentication_session") {
        put(Endpoint.UpdateUser.route) {
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            if (userSession != null) {
                try {
                    updateUserInfo(
                        app = app,
                        userDataSource = userDataSource,
                        userUpdate = userUpdate,
                        userId = userSession.id
                    )
                } catch (e: Exception) {
                    app.log.info("UNABLE TO UPDATE USER DATA")
                    call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
                }
            } else {
                app.log.info("INVALID SESSION DATA")
                call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
            }
        }
    }

}

private suspend fun PipelineContext<Unit, ApplicationCall>.updateUserInfo(
    app: Application,
    userDataSource: UserDataSource,
    userUpdate: UserUpdate,
    userId: String
) {
    val response = userDataSource.updateUserInfo(
        userId = userId,
        firstName = userUpdate.firstName,
        lastName = userUpdate.lastName
    )
    if (response) {
        call.respond(
            message = ApiResponse(
                success = true,
                message = "USER INFO HAS BEEN UPDATED"
            )
        )
    } else {
        app.log.info("CAN'T UPDATE USER INFO")
        call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
    }
}