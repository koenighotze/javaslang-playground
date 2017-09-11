package org.koenighotze.resilience4j;

import static io.vavr.collection.List.empty;
import static org.springframework.http.HttpStatus.OK;

import io.github.resilience4j.circuitbreaker.*;
import io.github.resilience4j.retry.*;
import io.vavr.*;
import io.vavr.collection.*;
import io.vavr.control.*;
import org.koenighotze.resilience4j.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartureBoardService {

    private final DbConsumer dbConsumer;
    private final CircuitBreaker circuitBreaker;
    private final Retry.Context retryContext;

    @Autowired
    public DepartureBoardService(DbConsumer dbConsumer, CircuitBreaker circuitBreaker, Retry.Context retryContext) {
        this.dbConsumer = dbConsumer;
        this.circuitBreaker = circuitBreaker;
        this.retryContext = retryContext;
    }

    @GetMapping("/station/{stationId}/departureboard")
    public HttpEntity<Option<List<Departure>>> getStations(@PathVariable("stationId") String stationId) {
        // @formatter:off

//      CheckedFunction<String, Option<List<Departure>>> decorated =
        CheckedFunction1<String,Option<List<Departure>>> decorated = CircuitBreaker.decorateCheckedFunction(circuitBreaker, dbConsumer::fetchDepartureBoard);
//                          .withRetry(retryContext)
//                          .decorate();

        Option<List<Departure>> result = Try.of(() -> decorated.apply(stationId))
                                            .getOrElse(Option.of(empty()));

        // @formatter:on
        return new ResponseEntity<>(result, OK);
    }
}
