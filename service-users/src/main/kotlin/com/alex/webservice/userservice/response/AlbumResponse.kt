package com.alex.webservice.userservice.response

data class AlbumResponse(
    val albumId: String,
    val userId: String,
    val name: String,
    val description: String
)