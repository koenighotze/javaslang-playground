package org.koenighotze.javaslangplayground;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.Test;

import io.vavr.collection.List;
import io.vavr.control.Option;

/**
 * @author David Schmitz
 */
public class FlatVsMapTest {

    @Test
    public void null_is_filtered_by_flatmap() {
        List<String> result =
            List.of("Foo", null, "Baz")
                .flatMap(Option::of)
                .take(3);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly("Foo", "Baz");
    }

}
