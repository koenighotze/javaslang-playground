package org.koenighotze.javaslangplayground.demo.demo3.javaslang;

import static org.koenighotze.javaslangplayground.demo.demo3.Response.OK;

import javaslang.collection.*;
import org.koenighotze.javaslangplayground.demo.demo3.*;

public class Refactored {

    private static String fetchTweets(Response response) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(fetchTweets(Response.of(OK, List.of("a", "b"))));

        System.out.println(fetchTweets(Response.notFound()));
    }


}
