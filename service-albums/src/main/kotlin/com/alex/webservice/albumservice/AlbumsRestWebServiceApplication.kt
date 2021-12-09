package com.alex.webservice.albumservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class AlbumsRestWebServiceApplication

fun main(args: Array<String>) {
	runApplication<AlbumsRestWebServiceApplication>(*args)
}
