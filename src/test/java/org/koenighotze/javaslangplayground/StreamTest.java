package org.koenighotze.javaslangplayground;

import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Collections.set;

import java.util.Set;

import org.junit.Test;

import javaslang.collection.Stream;

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
}
