package com.example.domain.model

import io.ktor.server.auth.*

data class UserSession(
    val name: String,
    val id: String
): Principal
