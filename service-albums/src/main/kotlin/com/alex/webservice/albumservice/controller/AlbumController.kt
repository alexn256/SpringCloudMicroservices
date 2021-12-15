package com.alex.webservice.albumservice.controller

import com.alex.webservice.albumservice.data.Album
import com.alex.webservice.albumservice.dto.AlbumResponse
import com.alex.webservice.albumservice.service.AlbumsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users/{id}/albums")
class AlbumController(
    val albumsService: AlbumsService
) {

    @GetMapping(
        produces = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
        ]
    )
    fun userAlbums(
        @PathVariable
        id: String
    ): ResponseEntity<List<AlbumResponse>> {
        val albums = albumsService.getAlbums(id)
        if (albums.isEmpty()) {
            return ResponseEntity(listOf(), HttpStatus.NOT_FOUND)
        }
        val result = albums.map { rec -> toAlbumResponseModel(rec) }.toList()
        return ResponseEntity(result, HttpStatus.OK)
    }

    fun toAlbumResponseModel(album: Album): AlbumResponse {
        return AlbumResponse(
            album.albumId,
            album.userId,
            album.name,
            album.description
        )
    }
}