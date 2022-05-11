package com.example

import io.ktor.server.application.*
import com.example.plugins.*
import configureKoin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    authentication()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    session()
}
