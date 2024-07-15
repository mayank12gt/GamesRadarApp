package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "feed", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom")
public class Feed implements Serializable {
    @ElementList(inline = true, entry = "entry")
    private List<Entry> entries;

    @Element(name = "script", required = false)
    private List<String> scripts;

    @Element(name = "link")
    @Path("feed")
    private Link selfLink;

    @Element(name = "id")
    private String id;

    @Element(name = "yt:channelId", required = false)
    @Namespace(reference = "http://www.youtube.com/xml/schemas/2015")
    private String channelId;

    @Element(name="title")
    private String title;

    @Element(name = "published")
    private String published;

    @Element(name = "author")
    private Author author;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<String> getScripts() {
        return scripts;
    }

    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "entries=" + entries +
                ", scripts=" + scripts +

                ", id='" + id + '\'' +
                ", channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", published='" + published + '\'' +
                ", author=" + author +
                '}';
    }
}
