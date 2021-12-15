package com.alex.webservice.albumservice.dto

data class AlbumResponse(
    val albumId: String,
    val userId: String,
    val name: String,
    val description: String
)

