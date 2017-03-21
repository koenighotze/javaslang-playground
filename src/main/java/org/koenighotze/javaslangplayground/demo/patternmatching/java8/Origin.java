package org.koenighotze.javaslangplayground.demo.patternmatching.java8;

import static org.koenighotze.javaslangplayground.demo.patternmatching.Response.OK;

import java.util.Collections;
import java.util.List;

import org.koenighotze.javaslangplayground.demo.patternmatching.Response;

public class Origin {
    public List<String> fetchTweets(Response res) {
        if (OK.equals(res.getStatusCode())) {
            return res.getBody().toJavaList();
        }
        return Collections.emptyList();
    }
}
