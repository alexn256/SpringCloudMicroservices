package com.alex.webservice.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
class UserRestWebServiceApplication {
	@Bean
	fun passwordEncoder() = BCryptPasswordEncoder()
	@Bean
	@LoadBalanced
	fun restTemplate() = RestTemplate()
}

fun main(args: Array<String>) {
	runApplication<UserRestWebServiceApplication>(*args)
}
