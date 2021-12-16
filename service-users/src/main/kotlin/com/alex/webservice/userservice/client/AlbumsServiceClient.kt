package com.alex.webservice.userservice.client

import com.alex.webservice.userservice.response.AlbumResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "albums-ws")
interface AlbumsServiceClient {
    @GetMapping("users/{id}/albums")
    fun getAlbums(
        @PathVariable
        id:String):List<AlbumResponse>
}