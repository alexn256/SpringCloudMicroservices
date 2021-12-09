package com.alex.webservice.albumservice.service.impl

import com.alex.webservice.albumservice.data.Album
import com.alex.webservice.albumservice.service.AlbumsService
import org.springframework.stereotype.Service

@Service
class AlbumsServiceImpl: AlbumsService {

    override fun getAlbums(userId: String): List<Album> {
        return mutableListOf(
            Album(1L,
                "albumId1",
                "userId1",
                "album 1 name",
                "album 1 description"),
            Album(1L,
                "albumId1",
                "userId1",
                "album 1 name",
                "album 1 description"),
        )
    }
}