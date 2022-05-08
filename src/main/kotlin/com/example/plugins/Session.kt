package com.example.plugins

import com.example.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

fun Application.session() {
    install(Sessions) {
        val secretEncryptedKey = hex("cb09b74997e75deb96354a4243d6f23d")
        val secretAuthKey = hex("84518333afe0ec596eafbf5447ae052c")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptedKey, secretAuthKey))
        }
    }
}