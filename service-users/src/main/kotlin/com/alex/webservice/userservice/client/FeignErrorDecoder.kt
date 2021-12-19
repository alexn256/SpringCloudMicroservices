package com.alex.webservice.userservice.client

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception
import java.lang.IllegalArgumentException

@Component
class FeignErrorDecoder(val env: Environment) : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception? = when (response.status()) {
        400 -> IllegalArgumentException(response.reason())
        404 -> {
            if (methodKey.contains("getAlbum")) {
                ResponseStatusException(
                    HttpStatus.valueOf(response.status()),
                    env.getProperty("albums.exceptions.albums-not-found")
                )
            } else {
                null
            }
        }
        else -> Exception(response.reason())
    }
}