package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "content", strict = false)
public class MediaContent implements Serializable {
    @Attribute(name = "url")
    private String url;

    @Attribute(name = "type")
    private String type;

    @Attribute(name = "width")
    private int width;

    @Attribute(name = "height")
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaContent{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}