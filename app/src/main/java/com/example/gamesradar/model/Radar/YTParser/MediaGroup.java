package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "group",strict = false )
public class MediaGroup implements Serializable {
    @Element(name = "title",required = false )
    private String mediaTitle;

    @Element(name = "content", required = false)
    private MediaContent mediaContent;

    @Element(name = "thumbnail", required = false)
    private MediaThumbnail mediaThumbnail;

    @Element(name = "description", required = false)
    private String mediaDescription;

    @Element(name = "community", required = false)
    private MediaCommunity mediaCommunity;

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public MediaContent getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(MediaContent mediaContent) {
        this.mediaContent = mediaContent;
    }

    public MediaThumbnail getMediaThumbnail() {
        return mediaThumbnail;
    }

    public void setMediaThumbnail(MediaThumbnail mediaThumbnail) {
        this.mediaThumbnail = mediaThumbnail;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public MediaCommunity getMediaCommunity() {
        return mediaCommunity;
    }

    public void setMediaCommunity(MediaCommunity mediaCommunity) {
        this.mediaCommunity = mediaCommunity;
    }
}