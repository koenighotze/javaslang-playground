package org.koenighotze.resilience4j;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import io.github.robwin.circuitbreaker.*;
import io.github.robwin.decorators.*;
import io.github.robwin.retry.internal.*;
import javaslang.collection.*;
import javaslang.control.*;
import javaslang.control.Try.*;
import org.koenighotze.resilience4j.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/station", produces = APPLICATION_JSON_UTF8_VALUE)
public class StationService {

    private final DbConsumer dbConsumer;
    private final CircuitBreaker circuitBreaker;
    private final RetryContext retryContext;

    @Autowired
    public StationService(DbConsumer dbConsumer, CircuitBreaker circuitBreaker, RetryContext retryContext) {
        this.dbConsumer = dbConsumer;
        this.circuitBreaker = circuitBreaker;
        this.retryContext = retryContext;
    }

    @GetMapping(path = "/{city}")
    public HttpEntity<Option<List<StationInfo>>> getStations(@PathVariable("city") String city) {
        // @formatter:off
        CheckedFunction<String, Option<List<StationInfo>>> decorated =
                Decorators.ofCheckedFunction(dbConsumer::fetchStations)
                          .withCircuitBreaker(circuitBreaker)
                          .withRetry(retryContext)
                          .decorate();

        Option<List<StationInfo>> result = Try.of(() -> decorated.apply(city))
                                               .getOrElse(Option.none());
        // @formatter:on
        return new ResponseEntity<>(result, result.map(r -> OK)
                                                  .getOrElse(NOT_FOUND));
    }



}
