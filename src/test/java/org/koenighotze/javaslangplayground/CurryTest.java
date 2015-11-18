package org.koenighotze.javaslangplayground;

import javaslang.*;
import org.junit.*;

import java.util.function.*;

import static org.fest.assertions.Assertions.*;

/**
 * @author dschmitz
 */
public class CurryTest {

    @Test
    public void curry_simple_function() {
        Function2<Integer, Integer, Integer> func = (i, j) -> i * j;

        assertThat(func.apply(3, 4)).isEqualTo(12);

        Function<Integer, Integer> doubleIt = func.curried().apply(2);

        assertThat(doubleIt.apply(12)).isEqualTo(24);
    }
}
