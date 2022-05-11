package com.example.di
import com.example.data.repository.UserDataSourceImpl
import com.example.domain.repository.UserDataSource
import com.example.util.Constants.USER_DATABASE
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(USER_DATABASE)
    }
    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
}