package com.example.domain.model

sealed class Endpoint(val route: String){
    object Root: Endpoint(route = "/")
    object TokenVerification: Endpoint(route = "/token_verification")
    object GetUser: Endpoint(route = "/get_user_info")
    object UpdateUser: Endpoint(route = "/update_user_info")
    object DeleteUser: Endpoint(route = "/delete_user")
    object SignIn: Endpoint(route = "/sign_in")
    object AuthorisedAccess: Endpoint(route = "/authorised_access")
    object UnAuthorisedAccess: Endpoint(route = "/unauthorised_access")
}

