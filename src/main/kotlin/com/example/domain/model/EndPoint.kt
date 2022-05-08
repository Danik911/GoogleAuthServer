package com.example.domain.model

sealed class EndPoint(val route: String){
    object Root: EndPoint(route = "/")
    object TokenVerification: EndPoint(route = "/token_verification")
    object GetUser: EndPoint(route = "/get_user_info")
    object UpdateUser: EndPoint(route = "/update_user_info")
    object DeleteUser: EndPoint(route = "/delete_user")
    object SignIn: EndPoint(route = "/sign_in")
    object AuthorisedAccess: EndPoint(route = "/authorised_access")
    object UnAuthorisedAccess: EndPoint(route = "/unauthorised_access")
}

