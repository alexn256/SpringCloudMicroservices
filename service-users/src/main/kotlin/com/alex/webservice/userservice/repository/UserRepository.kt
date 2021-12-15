package com.alex.webservice.userservice.repository

import com.alex.webservice.userservice.data.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findUserByEmail(email: String): User?
    fun findUserByUserId(userId: String): User?
}