package com.example.gamesradar.model.news;


import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;

import java.io.Serializable;


@Entity(tableName = "sources")
public class Source implements Serializable {
    @PrimaryKey()
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "isFollowed")

    private Boolean isFollowed;


    public Source(){

    }

    public Source(String name, String url, String imageUrl){
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
        this.isFollowed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

}
