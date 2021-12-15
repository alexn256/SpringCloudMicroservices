package com.alex.webservice.userservice.controller

import com.alex.webservice.userservice.dto.UserDto
import com.alex.webservice.userservice.request.user.UserDetailsRequest
import com.alex.webservice.userservice.response.UserDetailsResponse
import com.alex.webservice.userservice.response.UserResponse
import com.alex.webservice.userservice.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @Value("\${server.port}")
    lateinit var port: String
    @Value("\${remote.property.x}")
    lateinit var x: String
    @Value("\${remote.property.y}")
    lateinit var y: String

    @PostMapping
    fun createUser(@Valid @RequestBody details: UserDetailsRequest): ResponseEntity<UserDetailsResponse> {
        val userDto = details.toDto()
        val createdUser = userService.createUser(userDto)
        val body = createdUser.toUserDetailsResponse()
        return ResponseEntity(body, HttpStatus.CREATED)
    }

    @GetMapping("/status/check")
    fun checkStatus(): String {
        return "Working correctly on port =  $port, x = $x, y = $y"
    }

    @GetMapping("/{id}", produces = [
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    ])
    fun getUser(
        @PathVariable
        id:String
    ): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id)
        return ResponseEntity(user.toUserResponse(), HttpStatus.OK)
    }

    private fun UserDto.toUserResponse() = UserResponse(id, firstName, lastName, albums)
    private fun UserDto.toUserDetailsResponse() = UserDetailsResponse(firstName, lastName, email, id)
    private fun UserDetailsRequest.toDto() = UserDto(firstName, lastName, email, password)
}