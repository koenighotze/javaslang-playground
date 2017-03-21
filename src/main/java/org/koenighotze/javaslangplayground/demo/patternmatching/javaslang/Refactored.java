package org.koenighotze.javaslangplayground.demo.patternmatching.javaslang;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.collection.List.empty;
import static org.koenighotze.javaslangplayground.demo.patternmatching.Response.OK;
import static org.koenighotze.javaslangplayground.demo.patternmatching.Response.notFound;

import javaslang.API;
import javaslang.collection.List;
import org.koenighotze.javaslangplayground.demo.patternmatching.Response;

public class Refactored {
    private static List<String> fetchTweets(Response res) {
        return
            Match(res.getStatusCode())
            .of(
                Case($(OK), res::getBody),
                Case(API.<String>$(), List.<String>empty())
            );
    }

    private static List<String> fetchTweetsOptional(Response res) {
        return
            Match(res.getStatusCode())
                .option(
                    Case($(OK), res::getBody)
                )
                .getOrElse(empty());
    }

    public static void main(String[] args) {
        List<String> result = fetchTweets(Response.of(OK, List.of("Foo", "Bar")));
        System.out.println(result);
        System.out.println(fetchTweets(notFound()));
        System.out.println(fetchTweetsOptional(notFound()));
    }

}
