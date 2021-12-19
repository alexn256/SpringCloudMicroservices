package com.alex.webservice.userservice.service

import com.alex.webservice.userservice.client.AlbumsServiceClient
import com.alex.webservice.userservice.dto.UserDto
import com.alex.webservice.userservice.repository.UserRepository
import com.alex.webservice.userservice.response.AlbumResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val albumsClient: AlbumsServiceClient,
) : UserService {

    override fun createUser(user: UserDto): UserDto {
        user.id = UUID.randomUUID().toString()
        val entity = user.toEntity()
        val encryptedPassword = passwordEncoder.encode(user.password)
        entity.encryptedPassword = encryptedPassword
        userRepository.save(entity)
        return entity.toDto()
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findUserByEmail(email)
            ?: throw UsernameNotFoundException(email)
        return User(user.email, user.encryptedPassword, true, true, true, true, emptyList())
    }

    override fun getUserDetailsByEmail(email: String): UserDto {
        val user = userRepository.findUserByEmail(email)
            ?: throw UsernameNotFoundException(email)
        return user.toDto()
    }

    override fun getUserById(id: String): UserDto {
        val user = userRepository.findUserByUserId(id)
            ?: throw UsernameNotFoundException(id)
        val albums = albumsClient.getAlbums(id)
        return user.toDto(albums)
    }
}