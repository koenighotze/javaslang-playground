package org.koenighotze.javaslangplayground;

import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toSet;
import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Patterns.None;
import static javaslang.Patterns.Some;
import static javaslang.collection.List.empty;
import static javaslang.collection.List.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import javaslang.collection.List;
import javaslang.control.Option;

/**
 * @author David Schmitz
 */
public class OptionTest {
    @Test
    public void sample_usage() {
        Option<String> option = Option.of("foo");

        assertThat(option.getOrElse("other")).isEqualTo("foo");
    }

    @Test
    public void option_pattern_matched() {
        Option<String> option = Option.none();
        // @formatter:off
        String result = Match(option).of(
            Case(Some($()), identity()),
            Case(None(), "nix")
        );
        // @formatter:on
        assertThat(result).isEqualTo("nix");
    }

    @Test
    public void option_pattern_matched_with_guard() {
        Option<String> option = Option.of("bla");
        // @formatter:off
        String result = Match(option).of(
            Case(Some($(v -> v.length() > 4)), identity()),
            Case(Some($()), "other"),
            Case(None(), "nix")
        );
        // @formatter:on
        assertThat(result).isEqualTo("other");
    }

    @Test
    public void cascading_if_nulls() {
        org.koenighotze.javaslangplayground.plain.UserRepository repo = new org.koenighotze.javaslangplayground.plain.UserRepository();

        org.koenighotze.javaslangplayground.plain.User user = repo.findOne("id");
        String street = "";
        if (user != null) {
            Address address = user.getAddress();
            if (null != address) {
                street = address.getStreet();
            }
        }
        assertThat(street).as("Street should have been initialized").isNotNull();
    }

    @Test
    public void use_optinal_is_present() {
        org.koenighotze.javaslangplayground.plain.UserRepository repo = new org.koenighotze.javaslangplayground.plain.UserRepository();

        org.koenighotze.javaslangplayground.plain.User user = repo.findOne("id");
        Optional<org.koenighotze.javaslangplayground.plain.User> optional = Optional.ofNullable(user);
        if (optional.isPresent()) {

        }

        String street = "";
        if (user != null) {
            Address address = user.getAddress();
            if (null != address) {
                street = address.getStreet();
            }
        }
        assertThat(street).as("Street should have been initialized").isNotNull();
    }

    @Test
    public void fixing_things_with_option() {
        UserRepository repo = new UserRepository();
        String street = repo.findOne("id").flatMap(User::getAddress).map(Address::getStreet).getOrElse("");
        assertThat(street).as("Street should have been initialized").isNotNull();
    }

    @Test
    public void old_school_list() {
        java.util.List<String> roles = Arrays.asList("Foo", "Bar", "Baz");

        //noinspection ConstantConditions
        if (roles == null) {
            roles = emptyList();
        }

        java.util.Set<String> result = roles.stream().map(String::toUpperCase).collect(toSet());
        assertThat(result).contains("FOO", "BAR", "BAZ");
    }

    @Test
    public void fix_old_school_list() {
        List<String> roles = of("Foo", "Bar", "Baz");

        List<String> result =
            Option
                .of(roles)                  // Option<List<String>>
                .getOrElse(empty())         // List<String>
                .map(String::toUpperCase);  // List<String>

        assertThat(result).contains("FOO", "BAR", "BAZ");
    }
}




