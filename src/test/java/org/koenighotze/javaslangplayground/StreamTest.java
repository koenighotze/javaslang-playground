package org.koenighotze.javaslangplayground;

import javaslang.collection.Stream;
import org.junit.Test;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Collections.set;

/**
 * @author David Schmitz
 */
public class StreamTest {

    @Test
    public void standard_java8_stream() {
        //@formatter:off
        Set<String> teams =
            java.util.stream.Stream.of("F95", "FCK", "FCN")
              .map(String::toLowerCase)
              .collect(toSet());
        //@formatter:on
        assertThat(teams).isEqualTo(set("f95", "fck", "fcn"));
    }

    @Test
    public void javaslang_stream() {
        //@formatter:off
        Set<String> teams =
            Stream.of("F95", "FCK", "FCN")
                  .map(String::toLowerCase)
                  .toJavaSet();
        //@formatter:on
        assertThat(teams).isEqualTo(set("f95", "fck", "fcn"));
    }

    @Test
    public void read_multiple_times_from_stream() {
        Stream<String> sample = Stream.of("a", "B", "c");

        sample.map(String::toUpperCase);

        sample.map(String::toUpperCase);
    }

    @Test
    public void java8stream() {
        IntStream intStream = IntStream.of(1, 2, 3);

        intStream.map(i -> i - 2);
        intStream.map(i -> i - 2);
    }
}