package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "community", strict = false)
public class MediaCommunity implements Serializable {
    @Element(name = "starRating")
    private MediaStarRating starRating;

    @Element(name = "statistics")
    private MediaStatistics statistics;

    public MediaStarRating getStarRating() {
        return starRating;
    }

    public void setStarRating(MediaStarRating starRating) {
        this.starRating = starRating;
    }

    public MediaStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(MediaStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String toString() {
        return "MediaCommunity{" +
                "starRating=" + starRating +
                ", statistics=" + statistics +
                '}';
    }
}