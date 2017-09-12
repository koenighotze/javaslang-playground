package org.koenighotze.vavrplayground;

import static java.lang.String.join;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.koenighotze.vavrplayground.LiftTest.parseIban;

import java.io.*;
import java.util.stream.*;

import io.vavr.collection.*;
import io.vavr.control.*;
import org.junit.*;
import org.koenighotze.vavrplayground.plain.User;

/**
 * @author dschmitz
 */
public class ExceptionHandlingTest {

    private java.util.List<User> users;

    @Before
    public void setupUsers() {
        User validUser = new User();
        Address address = new Address();
        address.setStreet("Foolane");
        validUser.setAddress(address);

        users = asList(validUser, new User());
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
        String result = Try.of(() -> new FileReader().readFile(get("/etc/hosts")))
                           .map(liste -> join(", ", liste))
                           .getOrElse("Default");
        assertThat(result).isNotEmpty();
    }

    @Test
    public void handle_exceptions_in_classic_lamdba() {
        java.util.List<User> validUsers = users.stream()
                                               .filter(user -> {
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
    public void handle_exceptions_using_vavr() {
        List<User> validUsers = List.ofAll(users)
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

    @Test
    public void wrap_an_exception_with_try() {
        String result = Try.of(() -> parseIban("AL.."))
                           .getOrElse("");
        assertThat(result).isEqualTo("");

        // TODO: fix me
        //        result = Try.of(() -> parseIban("AL.."))
        //                    .recover(t -> Match(t).of(Case(instanceOf(IllegalArgumentException.class), "not an valid iban")))
        //                    .get();
        //
        //        assertThat(result).isEqualTo("not an valid iban");
    }
}
