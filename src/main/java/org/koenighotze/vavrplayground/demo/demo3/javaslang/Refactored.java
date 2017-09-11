package org.koenighotze.vavrplayground.demo.demo3.vavr;

import static org.koenighotze.vavrplayground.demo.demo3.Response.OK;

import io.vavr.collection.*;
import org.koenighotze.vavrplayground.demo.demo3.*;

public class Refactored {

    private static String fetchTweets(Response response) {
        List<String> original = List.of("baz");

        List<String> mod = original.append("qux");

        return null;
    }

    public static void main(String[] args) {
        System.out.println(fetchTweets(Response.of(OK, List.of("a", "b"))));

        System.out.println(fetchTweets(Response.notFound()));
    }


}
