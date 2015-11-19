package org.koenighotze.javaslangplayground;

import javaslang.control.*;
import org.junit.*;

import java.io.*;

import static java.lang.String.*;
import static java.nio.file.Paths.*;
import static org.fest.assertions.Assertions.*;

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
        String result = Try.of(() -> new FileReader().readFile(get("/etc/hosts")))
                .map(liste -> join(", ", liste))
                .get();

        assertThat(result).isNotEmpty();
    }

    @Test
    public void catch_exceptions_in_a_functional_way() {
        String result = Try.of(() -> new FileReader().readFile(get("/notthere")))
                .map(liste -> join(", ", liste))
                .orElseGet(t -> "File not found! Check param!");

        assertThat(result).isEqualTo("File not found! Check param!");
    }

}
