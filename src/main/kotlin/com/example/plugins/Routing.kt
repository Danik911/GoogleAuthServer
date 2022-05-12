package com.example.plugins

import com.example.domain.repository.UserDataSource
import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {

    routing {
        val userDataSource: UserDataSource by inject(UserDataSource::class.java)
        root()
        tokenVerification(application, userDataSource)
        unauthorisedUser()
        authorized()
        getUserInformationRoute(app = application, userDataSource = userDataSource)
        updateUserInfo(app = application, userDataSource = userDataSource)

    }
}
