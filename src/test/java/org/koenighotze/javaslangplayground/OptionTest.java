package org.koenighotze.javaslangplayground;

import javaslang.control.Option;
import org.junit.Test;

import static java.util.function.Function.identity;
import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Patterns.None;
import static javaslang.Patterns.Some;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author David Schmitz
 */
public class OptionTest {
    @Test
    public void sample_usage() {
        Option<String> option = Option.of("foo");

        assertThat(option.getOrElse("other"), is("foo"));
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
        assertThat(result, is("nix"));
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
        assertThat(result, is("other"));
    }

}


