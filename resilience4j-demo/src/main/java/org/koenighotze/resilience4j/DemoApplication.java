package org.koenighotze.resilience4j;

import java.time.*;

import com.fasterxml.jackson.databind.*;
import io.github.robwin.circuitbreaker.*;
import io.github.robwin.retry.*;
import io.github.robwin.retry.internal.*;
import javaslang.jackson.datatype.*;
import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.web.client.*;

@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(StationService.class);

    @Bean
    public static Module javaslangModule() {
        return new JavaslangModule();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(500);
        requestFactory.setConnectTimeout(500);
        return requestFactory;
    }

    @Bean
    public static RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory requestFactory, HttpMessageConverters httpMessageConverters) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(httpMessageConverters.getConverters());
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    public static RetryContext dbRetryContext() {
        RetryConfig retryConfig = RetryConfig.custom()
                                             .maxAttempts(2)
                                             .waitDuration(Duration.ofMillis(1000))
                                             .build();

        RetryContext retryContext = Retry.of("db", retryConfig);
        retryContext.getEventStream()
                    .map(Object::toString)
                    .forEach(logger::info);
        return retryContext;
    }

    @Bean
    public static CircuitBreaker dbCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                                                          .ringBufferSizeInClosedState(2)
                                                          .ringBufferSizeInHalfOpenState(2)
                                                          .waitDurationInOpenState(Duration.ofMillis(1000))
                                                          .recordFailure(failure -> {
                                                              logger.warn("Error recorded", failure);
                                                              return true;
                                                          })
                                                          .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("db", config);
        circuitBreaker.getEventStream()
                      .map(Object::toString)
                      .forEach(logger::info);
        return circuitBreaker;
    }

    public static void main(String... args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
