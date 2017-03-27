package org.koenighotze.resilience4j.model;

import com.fasterxml.jackson.annotation.*;
import javaslang.control.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Locations {
    private Option<LocationList> locationList;

    @JsonProperty("LocationList")
    public Option<LocationList> getLocationList() {
        return locationList;
    }

    public void setLocationList(LocationList locationList) {
        this.locationList = Option.of(locationList);
    }

    @Override
    public String toString() {
        return "Locations{" + "locationList=" + locationList + '}';
    }
}
