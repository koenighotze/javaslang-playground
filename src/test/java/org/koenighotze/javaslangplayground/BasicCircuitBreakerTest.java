package org.koenighotze.javaslangplayground;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.function.Function.identity;
import static javaslang.circuitbreaker.CircuitBreaker.State.CLOSED;
import static javaslang.circuitbreaker.CircuitBreaker.State.HALF_OPEN;
import static javaslang.circuitbreaker.CircuitBreaker.State.OPEN;
import static javaslang.circuitbreaker.CircuitBreaker.decorateCheckedSupplier;
import static javaslang.collection.List.empty;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.koenighotze.javaslangplayground.FeedFetcher.titanicFeed;

import java.time.Duration;
import java.util.stream.IntStream;

import javaslang.circuitbreaker.CircuitBreaker;
import javaslang.circuitbreaker.CircuitBreakerConfig;
import javaslang.circuitbreaker.CircuitBreakerRegistry;
import javaslang.collection.List;
import javaslang.control.Try;
import javaslang.control.Try.CheckedSupplier;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author David Schmitz
 */
public class BasicCircuitBreakerTest {
    private static final FeedFetcher feedFetcher = new FeedFetcher();
    private Counter counter;
    private CircuitBreaker circuitBreaker;
    private CheckedSupplier<List<String>> supplier;

    @Before
    public void initCircuitBreaker() {
        counter = new Counter();

        // @formatter:off
        circuitBreaker = CircuitBreakerRegistry.of(
                                    new CircuitBreakerConfig.Builder()
                                              .failureRateThreshold(20)
                                              .ringBufferSizeInClosedState(5) // after 5 tries, we'll open the circuit
                                              .ringBufferSizeInHalfOpenState(3) // after 3 tries, we'll either reopen or close
                                              .waitDurationInOpenState(Duration.of(2, SECONDS)) // after 2 secs we'll half-open the circuit
                                              .build())
                                    .circuitBreaker("circuit_opens_on_failure");
        // @formatter:on
        supplier = decorateCheckedSupplier(counter::fail, circuitBreaker);
    }

    @Test
    public void basic_circuit_breaker() {
        CircuitBreakerRegistry defaultRegistry = CircuitBreakerRegistry.of(CircuitBreakerConfig.ofDefaults());
        CircuitBreaker basic = defaultRegistry.circuitBreaker("basic");
        CheckedSupplier<List<String>> supplier = decorateCheckedSupplier(() -> feedFetcher.fetch(titanicFeed()), basic);
        assertThat(basic.getState()).isEqualTo(CLOSED);

        assertThat(Try.of(supplier)
                      .getOrElse(empty())).isNotNull();
        assertThat(basic.getState()).isEqualTo(CLOSED);
        assertThat(basic.getMetrics()
                        .getNumberOfBufferedCalls()).isEqualTo(1);
    }

    @Test
    public void circuit_opens_on_failure() throws InterruptedException {
        assertThat(circuitBreaker.getState()).isEqualTo(CLOSED);

        assertThatTheFirstFourCallsSucceed(circuitBreaker, supplier);

        assertThatTheFiftCallOpensTheCircuit(circuitBreaker, supplier);
    }

    @Test
    public void that_circuit_moves_to_half_open_after_duration() throws Throwable {
        assertThatCircuitIsOpen();

        counter.reset();

        assertThatCircuitIsHalfOpen();
    }

    @Test
    public void that_circuit_closes_if_error_rate_drops_below_threshold() throws Throwable {
        assertThatCircuitIsOpen();

        counter.reset();

        assertThatCircuitIsHalfOpen();

        IntStream.range(1, 5)
                 .forEach(i -> Try.of(supplier)
                                  .map(identity()));
        assertThat(circuitBreaker.getState()).isEqualTo(CLOSED);
    }

    private void assertThatCircuitIsHalfOpen() throws Throwable {
        Thread.sleep(5000);
        supplier.get(); // trigger supplier
        assertThat(circuitBreaker.getState()).isEqualTo(HALF_OPEN);
    }

    private void assertThatCircuitIsOpen() {
        IntStream.range(1, 6)
                 .forEach(i -> Try.of(supplier)
                                  .map(identity()));
        assertThat(circuitBreaker.getState()).isEqualTo(OPEN);
    }

    private void assertThatTheFiftCallOpensTheCircuit(CircuitBreaker circuitBreaker, CheckedSupplier<List<String>> supplier) {
        assertThat(Try.of(supplier)
                      .isFailure()).isTrue();
        assertThat(circuitBreaker.getMetrics()
                                 .getNumberOfFailedCalls()).isEqualTo(1);
        assertThat(circuitBreaker.getState()).isEqualTo(OPEN);
    }

    private void assertThatTheFirstFourCallsSucceed(CircuitBreaker circuitBreaker, CheckedSupplier<List<String>> supplier) {
        IntStream.range(1, 5)
                 .forEach(i -> Try.of(supplier)
                                  .map(identity()));
        assertThat(circuitBreaker.getState()).isEqualTo(CLOSED);
        assertThat(circuitBreaker.getMetrics()
                                 .getNumberOfFailedCalls()).isEqualTo(0);
    }

    @Test
    @Ignore("Check how to protect against timeouts")
    public void circuit_opens_on_timeout() {
    }

    private static class Counter {
        private int numberOfCalls = 0;

        public void reset() {
            numberOfCalls = 0;
        }

        public List<String> fail() {
            numberOfCalls++;
            if (numberOfCalls >= 5) {
                System.err.println("Woha...only 5 calls allowed!");
                throw new RuntimeException("Peng");
            }
            return List.of("" + numberOfCalls);
        }
    }
}