package com.example.domain.model

sealed class Endpoint(val route: String){
    object Root: Endpoint(route = "/")
    object TokenVerification: Endpoint(route = "/token_verification")
    object GetUser: Endpoint(route = "/get_user_info")
    object UpdateUser: Endpoint(route = "/update_user_info")
    object DeleteUser: Endpoint(route = "/delete_user")
    object SignOut: Endpoint(route = "/sign_out")
    object AuthorisedAccess: Endpoint(route = "/authorised_access")
    object UnAuthorisedAccess: Endpoint(route = "/unauthorised_access")
}

