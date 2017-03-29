package org.koenighotze.javaslangplayground;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javaslang.collection.*;
import org.junit.*;


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
        assertThat(teams).contains("f95", "fck", "fcn");
    }

    @Test
    public void javaslang_stream() {
        //@formatter:off
        Set<String> teams =
            Stream.of("F95", "FCK", "FCN")
                  .map(String::toLowerCase)
                  .toJavaSet();
        //@formatter:on
        assertThat(teams).contains("f95", "fck", "fcn");
    }

    @Test
    public void read_multiple_times_from_stream() {
        Stream<String> sample = Stream.of("a", "B", "c");

        assertThat(sample.map(String::toUpperCase).take(3)).contains("A", "B", "C");
        assertThat(sample.map(String::toLowerCase).take(3)).contains("a", "b", "c");
    }

    // cannot read multiple times from a jdk stream
    @Test(expected = IllegalStateException.class)
    public void java8stream() {
        java.util.stream.Stream<String> sample = java.util.stream.Stream.of("a", "B", "c");

        sample.map(String::toUpperCase);
        sample.map(String::toUpperCase);
    }
}
