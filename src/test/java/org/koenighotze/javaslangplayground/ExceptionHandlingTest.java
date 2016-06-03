package org.koenighotze.javaslangplayground;

import static java.lang.String.join;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.joining;
import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import javaslang.control.Try;

/**
 * @author dschmitz
 */
public class ExceptionHandlingTest {
    @Test
    public void reading_a_file_returns_all_lines() throws IOException {
        assertThat(new FileReader().readFile(get("/etc/hosts"))).isNotEmpty();
    }

    @Test(expected = IOException.class)
    public void reading_non_existing_files_fails() throws IOException {
        new FileReader().readFile(get("foo"));
    }

    @Test
    public void simple_try_usage() {
        String result = Try.of(() -> new FileReader().readFile(get("/etc/hosts"))).map(liste -> join(", ", liste)).get();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void catch_exceptions_in_a_functional_way() {
        // old
        String oldResult;
        try {
            //@formatter:off
            oldResult = new FileReader()
                                .readFile(get("/notthere"))
                                .stream()
                                .collect(joining(", "));
            //@formatter:on
        } catch (IOException e) {
            oldResult = "File not found! Check param!";
        }
        assertThat(oldResult).isEqualTo("File not found! Check param!");

        // new
        //@formatter:off
        String result =
            Try.of(() -> new FileReader().readFile(get("/notthere")))
                .map(lines -> join(", ", lines))
                .getOrElse("File not found! Check param!");
        //@formatter:on
        assertThat(result).isEqualTo("File not found! Check param!");
    }

}
