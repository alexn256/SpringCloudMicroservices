package com.alex.webservice.albumservice.data;

public class Album {

    private long id;
    private String albumId;
    private String userId;
    private String name;
    private String description;

    public Album(long id, String albumId, String userId, String name, String description) {
        this.id = id;
        this.albumId = albumId;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
