package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;

import java.io.Serializable;


@Root(name = "entry", strict = false)
    public class Entry implements Serializable {

        @Element(name = "id")
        private String id;
        @Element(name = "videoId",required = false)
        @Namespace(reference = "http://www.youtube.com/xml/schemas/2015")
        private String video_id;
        @Element(name = "channelId", required = false)
        @Namespace(reference = "http://www.youtube.com/xml/schemas/2015")
        private String channel_id;
        @Element(name = "link")

        private Link link;

        @Element(name = "title")
        private String title;

        @Element(name = "published")
        private String published;

        @Element(name = "author")
        private Author author;

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        @Element(name = "updated")
        private String updated;

        @Element(name = "group",required = false)
        @Namespace(reference = "http://search.yahoo.com/mrss/")

        private MediaGroup mediaGroup;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public MediaGroup getMediaGroup() {
            return mediaGroup;
        }

        public void setMediaGroup(MediaGroup mediaGroup) {
            this.mediaGroup = mediaGroup;
        }
    }

