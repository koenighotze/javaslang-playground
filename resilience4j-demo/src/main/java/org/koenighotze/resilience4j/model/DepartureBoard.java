package org.koenighotze.resilience4j.model;

import com.fasterxml.jackson.annotation.*;
import javaslang.collection.*;
import javaslang.control.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartureBoard {

    public static class DepartureBoardList {
        private List<Departure> departures;

        @JsonProperty("Departure")
        public List<Departure> getDepartures() {
            return departures;
        }

        public void setDepartures(List<Departure> departures) {
            this.departures = departures;
        }

        @Override
        public String toString() {
            return "DepartureBoardList{" + "departures=" + departures + '}';
        }
    }

    private DepartureBoardList departureBoardList;

    @JsonProperty("DepartureBoard")
    public DepartureBoardList getDepartureBoardList() {
        return departureBoardList;
    }

    public void setDepartureBoardList(DepartureBoardList departureBoardList) {
        this.departureBoardList = departureBoardList;
    }

    public Option<List<Departure>> getDepartures() {
        return null == departureBoardList ? Option.none() : Option.of(departureBoardList.getDepartures());
    }

    @Override
    public String toString() {
        return "DepartureBoard{" + "departureBoardList=" + departureBoardList + '}';
    }
}
