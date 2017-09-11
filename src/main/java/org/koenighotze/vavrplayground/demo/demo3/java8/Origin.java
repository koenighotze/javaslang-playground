package org.koenighotze.vavrplayground.demo.demo3.java8;


import static io.vavr.collection.List.empty;
import static org.koenighotze.vavrplayground.demo.demo3.Response.OK;

import io.vavr.collection.List;
import org.koenighotze.vavrplayground.demo.demo3.Response;

public class Origin {
    public static List<String> fetchTweets(Response res) {
        if (OK.equals(res.getStatusCode())) {
            return res.getBody();
        }

        return empty();
    }

    public static void main(String[] args) {
        System.out.println(fetchTweets(Response.of(OK, List.of("a", "b"))));

        System.out.println(fetchTweets(Response.notFound()));

    }
}
