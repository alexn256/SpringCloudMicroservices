package com.alex.webservice.userservice.security.filter

import com.alex.webservice.userservice.request.login.LoginRequest
import com.alex.webservice.userservice.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val userService: UserService,
    authManager: AuthenticationManager,
    private val expiration: String,
    private val secretKey: String
) : UsernamePasswordAuthenticationFilter() {

    init {
        super.setAuthenticationManager(authManager)
    }

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val credentials = mapper.readValue(req.inputStream, LoginRequest::class.java)
            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(credentials.email, credentials.password, mutableListOf())
            )
        } catch (e: IOException) {
            throw RuntimeException()
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val username = (auth.principal as User).username
        val user = userService.getUserDetailsByEmail(username)
        val token = Jwts.builder()
            .setSubject(user.id)
            .setExpiration(Date(System.currentTimeMillis() + expiration.toLong()))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
        response.addHeader("token", token)
        response.addHeader("userId", user.id)
    }
}