package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "statistics", strict = false)
public class MediaStatistics implements Serializable {
    @Attribute(name = "views")
    private int views;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "MediaStatistics{" +
                "views=" + views +
                '}';
    }
}
