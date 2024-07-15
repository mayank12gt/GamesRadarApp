package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "link", strict = false)
public class Link implements Serializable {
    @Attribute(name = "rel")
    private String rel;

    @Attribute(name = "href")
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}