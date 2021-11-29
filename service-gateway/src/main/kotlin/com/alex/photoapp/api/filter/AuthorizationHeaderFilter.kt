package com.alex.photoapp.api.filter

import com.google.common.net.HttpHeaders.AUTHORIZATION
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class AuthorizationHeaderFilter(config: Class<Config>) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(config),
    Ordered {

    @Value("\${jwt.token.secret}")
    lateinit var secret: String

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            println("First of all, the main AuthorizationHeaderFilter is executed...")
            val request = exchange.request
            if (!request.headers.containsKey(AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }
            val authorizationHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
            val jwt = authorizationHeader?.replace("Bearer", "")
            if (!isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }
            return@GatewayFilter chain.filter(exchange)
        }
    }

    private fun isJwtValid(jwt: String?): Boolean {
        val subject: String?
        try {
            subject = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).body.subject
        } catch (ex: Exception) {
            return false
        }
        if (subject == null || subject.isEmpty()) {
            return false
        }
        return true
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void?> {
        val response = exchange.response
        response.statusCode = httpStatus
        return response.setComplete()
    }

    override fun getOrder(): Int = 0

    companion object Config {

    }
}