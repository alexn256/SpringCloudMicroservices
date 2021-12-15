package com.alex.webservice.userservice.dto

import com.alex.webservice.userservice.response.AlbumResponse

data class UserDto(
    val firstName : String,
    val lastName: String,
    val email: String,
    var password: String = "",
    var id: String = "",
    var encryptedPassword: String = "",
    var albums: List<AlbumResponse> = listOf()
): java.io.Serializable {
    companion object {
        private const  val serialVersionUID = -8441L
    }
}






