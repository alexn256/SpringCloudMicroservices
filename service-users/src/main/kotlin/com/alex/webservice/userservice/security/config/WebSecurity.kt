package com.alex.webservice.userservice.security.config

import com.alex.webservice.userservice.security.filter.AuthenticationFilter
import com.alex.webservice.userservice.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurity(
    val userService: UserService,
    val passwordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    @Value("\${login.url.path}")
    lateinit var loginUrl: String
    @Value("\${jwt.token.expiration}")
    lateinit var expiration: String
    @Value("\${jwt.token.secret}")
    lateinit var secretKey: String

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.authorizeRequests().antMatchers("/**").permitAll()
        http.addFilter(authenticationFilter())
        http.headers().frameOptions().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
    }

    private fun authenticationFilter(): AuthenticationFilter {
        val filter = AuthenticationFilter(userService, authenticationManager(), expiration, secretKey)
        filter.setAuthenticationManager(authenticationManager())
        filter.setFilterProcessesUrl(loginUrl)
        return filter
    }
}