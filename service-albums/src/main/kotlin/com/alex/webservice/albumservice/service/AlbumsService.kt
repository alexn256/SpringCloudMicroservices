package com.alex.webservice.albumservice.service

import com.alex.webservice.albumservice.data.Album

interface AlbumsService {
    fun getAlbums(userId: String): List<Album>
}