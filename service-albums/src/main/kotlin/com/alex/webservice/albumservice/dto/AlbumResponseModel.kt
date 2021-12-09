package com.alex.webservice.albumservice.dto

data class AlbumResponseModel(
    val albumId: String,
    val userId: String,
    val name: String,
    val description: String
)

