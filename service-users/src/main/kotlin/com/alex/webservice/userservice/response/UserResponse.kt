package com.alex.webservice.userservice.response

data class UserResponse(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val albums: List<AlbumResponse>?
)