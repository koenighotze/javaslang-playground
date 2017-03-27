package org.koenighotze.javaslangplayground.demo.demo3.java8;


import static org.koenighotze.javaslangplayground.demo.demo3.Response.OK;

import javaslang.collection.List;
import org.koenighotze.javaslangplayground.demo.demo3.Response;

public class Origin {
    public javaslang.collection.List<String> fetchTweets(Response res) {
        if (OK.equals(res.getStatusCode())) {
            return res.getBody();
        }

        return List.empty();
    }
}
