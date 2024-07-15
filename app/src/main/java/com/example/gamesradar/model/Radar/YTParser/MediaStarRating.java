package com.example.gamesradar.model.Radar.YTParser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "starRating", strict = false)

public class MediaStarRating implements Serializable {
    @Attribute(name = "count")
    private int count;

    @Attribute(name = "average")
    private double average;

    @Attribute(name = "min")
    private int min;

    @Attribute(name = "max")
    private int max;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "MediaStarRating{" +
                "count=" + count +
                ", average=" + average +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
