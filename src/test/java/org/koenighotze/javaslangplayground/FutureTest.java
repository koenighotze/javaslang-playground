package org.koenighotze.javaslangplayground;

import javaslang.concurrent.Future;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.After;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author dschmitz
 */
public class FutureTest {
    private static final Logger LOGGER = Logger.getLogger(FutureTest.class.getName());

    public static final String MESSAGE = "Foo after so many seconds :(";
    private Future<String> longRunningFuture;

    @After
    public void tearDown() {
        if (null != longRunningFuture && !longRunningFuture.isCompleted()) {
            longRunningFuture.cancel();
        }
    }

    @Test
    public void simple_future() {
        longRunningFuture = createTask();

        assertThat(longRunningFuture.isCompleted()).isFalse();

        longRunningFuture.await();

        assertThat(longRunningFuture.isCompleted()).isTrue();

        assertThat(longRunningFuture.get()).isEqualTo(MESSAGE);
    }

    @Test
    public void cancel_a_future() {
        longRunningFuture = createTask();
        assertThat(longRunningFuture.cancel()).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void a_canceled_future_throws_an_ex() {
        longRunningFuture = createTask();
        longRunningFuture.cancel();

        longRunningFuture.get();
    }

    @Test
    public void the_value_of_a_canceled_future_is_defined() {
        longRunningFuture = createTask();
        longRunningFuture.cancel();

        Option<Try<String>> value = longRunningFuture.getValue();

        assertThat(value.isDefined()).isTrue();

        assertThat(value.get().getOrElse("Othertext")).isEqualTo("Othertext");

    }

    @Test
    public void the_value_of_a_running_future_is_not_defined() {
        longRunningFuture = createTask();

        Option<Try<String>> value = longRunningFuture.getValue();

        assertThat(value.isDefined()).isFalse();
    }

    @Test
    public void filtering_a_future() {
        longRunningFuture = createTask();

        // TODO
//        String result = longRunningFuture.filter(s -> s.length() > 5).ifDefined(() -> "yeah!", () -> "Neah!");

//        assertThat(result).isEqualTo("yeah!");
    }

    private Future<String> createTask() {
        return Future.of(() -> {
            LOGGER.info("Running a more or less short lived task...");
            sleep(SECONDS.toMillis(2L));
            return MESSAGE;
        });
    }
}
