package org.koenighotze.javaslangplayground;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import javaslang.API;
import javaslang.API.Match;
import javaslang.API.Match.Case;
import javaslang.collection.HashMap;
import javaslang.control.Option;

/**
 * @author David Schmitz
 */
public class MapTest {
    @Test
    public void mapsAreImmutable() {
        HashMap<String, String> map = HashMap.of("Foo", "Bar", "Qux", "Baz");
        assertThat(map.get("Foo").get()).isEqualTo("Bar");
    }

    @Test
    public void bimap() {
        Option<String> quxValue = HashMap.<String, String>of("Foo", "Bar", "Qux", "Baz")
                .bimap(String::toLowerCase, String::toUpperCase)
                .get("Qux");

        assertThat(quxValue.isEmpty()).isTrue();
    }

}
