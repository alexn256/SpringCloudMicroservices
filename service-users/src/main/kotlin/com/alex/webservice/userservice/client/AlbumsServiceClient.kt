package com.alex.webservice.userservice.client

import com.alex.webservice.userservice.response.AlbumResponse
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "albums-ws",
    fallbackFactory = AlbumsFallbackFactory::class)
interface AlbumsServiceClient {
    @GetMapping("/users/{id}/albums")
    fun getAlbums(
        @PathVariable
        id:String):List<AlbumResponse>
}

@Component
class AlbumsFallbackFactory: FallbackFactory<AlbumsServiceClient> {
    override fun create(cause: Throwable) = AlbumsServiceClientFallback(cause)
}

class AlbumsServiceClientFallback(private val cause: Throwable): AlbumsServiceClient {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAlbums(id: String): List<AlbumResponse> {
        if (cause is FeignException && cause.status() == 404) {
            logger.error("404 error took place when getAlbums was called with userId: $id. Error message: ${cause.localizedMessage}")
        } else {
            logger.error("Other error took place: ${cause.localizedMessage}")
        }
        return listOf()
    }
}