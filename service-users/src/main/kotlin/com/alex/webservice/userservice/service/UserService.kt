package com.alex.webservice.userservice.service

import com.alex.webservice.userservice.data.User
import com.alex.webservice.userservice.dto.UserDto
import com.alex.webservice.userservice.response.AlbumResponse
import com.alex.webservice.userservice.response.UserResponse
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun createUser(user: UserDto): UserDto
    fun getUserDetailsByEmail(email: String): UserDto
    fun getUserById(id: String): UserDto
}

internal fun User.toDto(albums: List<AlbumResponse> = listOf()) = UserDto(
    firstName,
    lastName,
    email,
    id = userId,
    encryptedPassword = encryptedPassword,
    albums = albums
)

internal fun UserDto.toEntity(): User {
    val user = User()
    user.firstName = firstName
    user.lastName = lastName
    user.email = email
    user.userId = id
    user.encryptedPassword = encryptedPassword
    return user
}