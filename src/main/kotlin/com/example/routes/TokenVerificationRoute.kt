package com.example.routes

import com.example.domain.model.ApiRequest
import com.example.domain.model.Endpoint
import com.example.domain.model.User
import com.example.domain.model.UserSession
import com.example.domain.repository.UserDataSource
import com.example.util.Constants.AUDIENCE
import com.example.util.Constants.ISSUER
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*

fun Routing.tokenVerification(app: Application, userDataSource: UserDataSource) {
    post(Endpoint.TokenVerification.route) {
        val request = call.receive<ApiRequest>()
        if (request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(request.tokenId)
            if (result != null) {
                saveUserToDatabase(app = app, result = result, userDataSource = userDataSource)
                app.log.info("TokenId successfully verified")
            } else {
                call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
                app.log.info("TokenId is null")
            }
        } else {
            call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
            app.log.info("TokenId is empty")
        }

    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    app: Application,
    result: GoogleIdToken,
    userDataSource: UserDataSource
) {
    val sub = result.payload["sub"].toString()
    val name = result.payload["name"].toString()
    val emailAddress = result.payload["email"].toString()
    val profilePhoto = result.payload["picture"].toString()
    val user = User(
        id = sub,
        name = name,
        email = emailAddress,
        profilePicture = profilePhoto
    )

    val response = userDataSource.saveUserInfo(user = user)
    if (response) {
        app.log.info("USER SUCCESSFULLY SAVED/RETRIEVED")
        call.sessions.set(UserSession(id = sub, name = name))
        call.respondRedirect(Endpoint.AuthorisedAccess.route)
    } else {
        app.log.info("ERROR SAVING THE USER")
        call.respondRedirect(Endpoint.UnAuthorisedAccess.route)
    }
}

fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? {
    return try {
        val googleIdTokenVerifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setIssuer(ISSUER)
            .setAudience(listOf(AUDIENCE))
            .build()
        googleIdTokenVerifier.verify(tokenId)
    } catch (e: Exception) {
        return null
    }

}