package com.alex.photoapp.api

import com.alex.photoapp.api.filter.AuthorizationHeaderFilter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@EnableEurekaClient
@SpringBootApplication
class ApiGatewayApplication {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Order(value = 1)
    @Bean
    fun firstGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            logger.info("First Pre-Filter is executed...")
            return@GlobalFilter chain.filter(exchange)
                .then(Mono.fromRunnable {
                    logger.info("Third Post-Filter is executed...")
                })
        }
    }

    @Order(value = 2)
    @Bean
    fun secondGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            logger.info("Second Pre-Filter is executed...")
            return@GlobalFilter chain.filter(exchange)
                .then(Mono.fromRunnable {
                    logger.info("Second Post-Filter is executed...")
                })
        }
    }

    @Order(value = 3)
    @Bean
    fun thirdGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            logger.info("Third Pre-Filter is executed...")
            return@GlobalFilter chain.filter(exchange)
                .then(Mono.fromRunnable {
                    logger.info("First Post-Filter is executed...")
                })
        }
    }

    @Bean
    fun authorizationHeaderFilter() = AuthorizationHeaderFilter(AuthorizationHeaderFilter.Config::class.java)
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
