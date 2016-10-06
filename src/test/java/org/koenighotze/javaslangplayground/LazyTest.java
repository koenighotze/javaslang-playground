package org.koenighotze.javaslangplayground;

import javaslang.Lazy;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

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

        // TODO
//        String result = lazyCalc.filter((s) -> s.contains("Foobar"))
//                                    .ifDefined("Strange UUID", "Everything is peachy!");
//
//        assertThat(result).isEqualTo("Everything is peachy!");
    }

}
