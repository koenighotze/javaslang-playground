package org.koenighotze.resilience4j;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.collection.List.empty;
import static org.springframework.http.HttpStatus.OK;

import java.util.function.*;

import io.vavr.collection.*;
import io.vavr.control.*;
import org.apache.http.impl.client.*;
import org.koenighotze.resilience4j.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class DbConsumer {
    private final String dbApiStationUrlTemplate;
    private final String dbApiDepartureBoardUrlTemplate;
    private final String authKey;
    private final RestTemplate restTemplate;

    @Autowired
    public DbConsumer(@Value("${db.api.station.url.template}") String dbApiStationUrlTemplate, @Value("${db.api.departureboard.url.template}") String dbApiDepartureBoardUrlTemplate, @Value("${db.api.auth.key}") String authKey, RestTemplate restTemplate) {
        this.dbApiStationUrlTemplate = dbApiStationUrlTemplate;
        this.dbApiDepartureBoardUrlTemplate = dbApiDepartureBoardUrlTemplate;
        this.authKey = authKey;
        this.restTemplate = restTemplate;
    }

    public Option<List<StationInfo>> fetchStations(String city) {
        ResponseEntity<Locations> result = restTemplate.getForEntity(dbApiStationUrlTemplate, Locations.class, city,
            authKey);

        return Match(result.getStatusCode()).option(Case($(OK), extractStationInfo(result)));
    }

    private Supplier<List<StationInfo>> extractStationInfo(ResponseEntity<Locations> result) {
        return () -> result.getBody()
                           .getLocationList()
                           .map(LocationList::getLocations)
                           .map(Array::toList)
                           .getOrElse(empty());
    }

    public Option<List<Departure>> fetchDepartureBoard(String stationId) {
        ResponseEntity<DepartureBoard> result =
            restTemplate.getForEntity(dbApiDepartureBoardUrlTemplate,
                                      DepartureBoard.class, stationId, authKey);

        return Match(result.getStatusCode())
                    .option(Case($(OK), () -> result.getBody()
                                                    .getDepartures()
                                                    .getOrElse(empty())));
    }
}
