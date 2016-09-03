package org.koenighotze.javaslangplayground;

import javaslang.Function1;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author David Schmitz
 */
public class LiftTest {

    public static String unsafe(String s) {
        if (s.length() > 5) {
            return s.toUpperCase();
        }

        throw new IllegalArgumentException(s + " is too short");
    }

    @Test
    public void safe_op() {
        //@formatter:off
        String res =
            Function1
            .lift(LiftTest::unsafe)
            .apply("a")
            .getOrElse("a is too short");
        //@formatter:on
        assertThat(res).isEqualTo("a is too short");
    }

}
