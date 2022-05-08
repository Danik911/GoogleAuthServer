package com.example.plugins

import com.example.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

fun Application.session() {
    install(Sessions) {
        val secretEncryptedKey = hex("13b6e456116ad18f")
        val secretAuthKey = hex("491abbe235820cc9")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptedKey, secretAuthKey))
        }
    }
}