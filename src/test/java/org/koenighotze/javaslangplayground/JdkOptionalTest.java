package org.koenighotze.javaslangplayground;

import javaslang.control.Option;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JdkOptionalTest {
    @Test
    public void optional_does_not_hold_left_identity() {
        assertThat(Optional.of("Foo")
                .flatMap(str -> Optional.of(str.toUpperCase()))
                .map(String::toLowerCase).orElseGet(() -> "Baz"), is("foo"));
    }


    public static String mapValueToBar(String value) {
        return "Bar";
    }

    @Test
    public void optional_of_nullable_and_empty() {
        Map<String, String> map = new HashMap<>();
        map.put("A Null", null);
        map.put("A Non Null", "Foo");

        assertThat(Optional.ofNullable(map.get("A Non Null")).map(JdkOptionalTest::mapValueToBar).orElse("Qux"), is("Bar"));
        assertThat(Optional.ofNullable(map.get("A Null")).map(JdkOptionalTest::mapValueToBar).orElse("Qux"), is("Qux"));
    }

    @Test
    public void optional_of_nullable_and_empty_with_javaslang() {
        Map<String, String> map = new HashMap<>();
        map.put("A Null", null);
        map.put("A Non Null", "Foo");

        assertThat(Option.of(map.get("A Non Null")).map(JdkOptionalTest::mapValueToBar).getOrElse("Qux"), is("Bar"));
        assertThat(Option.of(map.get("A Null")).map(JdkOptionalTest::mapValueToBar).getOrElse("Qux"), is("Qux"));
    }


    @Test
    public void using_null_values_with_jdk_optional() {
        Map<String, String> map = new HashMap<>();
        map.put("A Null", null);
        map.put("A Non Null", "Foo");

        Function<String, String> f = (String s) -> {
            if (s == null) {
                return "";
            }
            return s.toUpperCase();
        };

        assertThat(Optional.ofNullable(f.apply(map.get("A Null"))).orElse("Qux"), is(""));
        assertThat(Optional.ofNullable(map.get("A Null")).map(f).orElse("Qux"), is("Qux"));

    }

    @Test
    public void using_null_values_with_option() {
        Map<String, String> map = new HashMap<>();
        map.put("A Null", null);
        map.put("A Non Null", "Foo");

        Function<String, String> f = (String s) -> {
            if (s == null) {
                return "";
            }
            return s.toUpperCase();
        };

        assertThat(Option.of(f.apply(map.get("A Null"))).getOrElse("Qux"), is(""));
        assertThat(Option.of(map.get("A Null")).map(f).getOrElse("Qux"), is("Qux"));
    }

}
