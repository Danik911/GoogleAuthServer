package com.example.routes

import com.example.domain.model.ApiRequest
import com.example.domain.model.Endpoint
import com.example.domain.model.UserSession
import com.example.util.Constants.AUDIENCE
import com.example.util.Constants.ISSUER
import com.fasterxml.jackson.core.JsonFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Routing.tokenVerification(app: Application) {
    post(Endpoint.TokenVerification.route) {
        val request = call.receive<ApiRequest>()
        if (request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(request.tokenId)
            if (result != null) {

                val emailAddress = result.payload["email"].toString()
                val name = result.payload["name"].toString()
                call.sessions.set(UserSession(name = "Daniil", id = "123"))
                call.respondRedirect(Endpoint.AuthorisedAccess.route)
                app.log.info("TokenId successfully verified, $emailAddress, $name")
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

fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? {
    return try {
        val googleIdTokenVerifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setIssuer(ISSUER)
            .setAudience(listOf(AUDIENCE))
            .build()
         googleIdTokenVerifier.verify(tokenId)
    } catch (e: Exception){
        return null
    }

}