package com.example.routes

import com.example.domain.model.EndPoint
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.root(){
    get(EndPoint.Root.route) {
        call.respondText("Welcome to Ktor server!")
    }
}