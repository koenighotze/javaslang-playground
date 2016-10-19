package org.koenighotze.javaslangplayground;

import static java.lang.String.join;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.koenighotze.javaslangplayground.plain.User;
import org.koenighotze.javaslangplayground.plain.UserRepository;

import javaslang.collection.List;
import javaslang.control.Try;

/**
 * @author dschmitz
 */
public class ExceptionHandlingTest {

    private java.util.List<User> users;
    private UserRepository repo;

    @Before
    public void setupUsers() {
        User validUser = new User();
        Address address = new Address();
        address.setStreet("Foolane");
        validUser.setAddress(address);

        users = asList(validUser, new User());
        repo = new UserRepository();
    }

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

    public static class MyBusinessException extends Exception {

    }

    @Test
    public void handle_exceptions_in_classic_lamdba() {
        java.util.List<User> validUsers =
                users.stream()
                .filter(user ->
                {
                    try {
                        return user.validateAddress();
                    } catch (IllegalStateException isex) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        assertThat(validUsers.size()).isEqualTo(1);
    }

    @Test
    public void handle_exceptions_using_javaslang() {
        List<User> validUsers =
                List.ofAll(users)
                        .filter(user -> Try.of(user::validateAddress)
                                .getOrElse(false));

        assertThat(validUsers.size()).isEqualTo(1);
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

    @Test
    public void apply_checked_exception_function_to_list_of_users() {
        // todo
    }
}
