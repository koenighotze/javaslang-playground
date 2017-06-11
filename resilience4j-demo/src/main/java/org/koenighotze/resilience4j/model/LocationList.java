package org.koenighotze.resilience4j.model;

import com.fasterxml.jackson.annotation.*;
import io.vavr.collection.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationList {
    private Array<StationInfo> locations = Array.empty();

    @JsonProperty("StopLocation")
    public Array<StationInfo> getLocations() {
        return locations;
    }

    public void setLocations(Array<StationInfo> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "LocationList{" + "locations=" + locations + '}';
    }
}
