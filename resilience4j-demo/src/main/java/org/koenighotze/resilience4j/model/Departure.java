package org.koenighotze.resilience4j.model;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Departure {
    private String date;
    private String direction;
    private String name;
    private String stop;
    private String stopid;
    private String time;
    private String track;
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Departure{" + "date='" + date + '\'' + ", direction='" + direction + '\'' + ", name='" + name + '\'' + ", stop='" + stop + '\'' + ", stopid='" + stopid + '\'' + ", time='" + time + '\'' + ", track='" + track + '\'' + ", type='" + type + '\'' + '}';
    }
}
