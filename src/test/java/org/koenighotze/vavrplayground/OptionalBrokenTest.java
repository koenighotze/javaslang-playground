package org.koenighotze.vavrplayground;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.koenighotze.vavrplayground.Lists.map;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author David Schmitz
 */
public class OptionalBrokenTest {
    
    @Test
    public void single_map_retails_order_and_structure() {
        Function<String, String> identity = s -> s;
        List<String> first = Arrays.asList("A", "B");
        List<String> result = map(first, identity);

        assertThat(first).isEqualTo(result);
    }

    @Test
    public void mapping_two_functions() {
        final Function<String, Integer> lengthFunc = String::length;
        final Function<Integer, Integer> intFunc = i -> i + 3;
        List<String> first = Arrays.asList("four", "five", "six");

        List<Integer> length = map(first, lengthFunc);
        List<Integer> result = Lists.map(length, intFunc);

        assertThat(result).containsExactly(7, 7, 6);

        assertThat(Lists.map(first, intFunc.compose(lengthFunc))).containsExactly(7, 7, 6);
    }

    @Test
    public void jdk_optional_with_null() {
        final Function<String, Optional<Integer>> lengthFunc = s -> Optional.ofNullable(null == s ? null : s.length());
        final Function<Optional<Integer>, Integer> intFunc = i -> i.map(j -> j + 3).orElse(0);
        List<String> first = Arrays.asList("four", null, "five", null, "six");

        List<Optional<Integer>> length = map(first, lengthFunc);
        List<Integer> result = Lists.map(length, intFunc);

        assertThat(result).containsExactly(7, 0, 7, 0, 6);
        assertThat(Lists.map(first, intFunc.compose(lengthFunc))).containsExactly(7, 0, 7, 0, 6);
    }
}
