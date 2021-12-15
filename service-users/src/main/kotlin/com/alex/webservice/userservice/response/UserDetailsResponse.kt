package com.alex.webservice.userservice.response

data class UserDetailsResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val userId: String
)