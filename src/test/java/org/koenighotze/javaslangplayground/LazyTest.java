package org.koenighotze.javaslangplayground;

import javaslang.*;
import org.junit.*;

import static java.util.UUID.*;
import static org.fest.assertions.Assertions.*;

/**
 * @author dschmitz
 */
public class LazyTest {
    @Test
    public void basic_stuff() {
        Lazy<String> lazyCalc = Lazy.of(() -> randomUUID().toString());

        assertThat(lazyCalc.get()).isEqualTo(lazyCalc.get());
    }

    @Test
    public void filtering_a_lazy_value() {
        Lazy<String> lazyCalc = Lazy.of(() -> randomUUID().toString());

        String result = lazyCalc.filter((s) -> s.contains("Foobar"))
                                    .ifDefined("Strange UUID", "Everything is peachy!");

        assertThat(result).isEqualTo("Everything is peachy!");
    }

}
