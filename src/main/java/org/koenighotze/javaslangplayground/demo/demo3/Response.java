package org.koenighotze.javaslangplayground.demo.demo3;

import io.vavr.collection.List;

/**
 * @author David Schmitz
 */
public class Response {
    public static final String OK = "200";
    private final String statusCode;
    private final List<String> body;

    public Response(String statusCode, List<String> body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public List<String> getBody() {
        return body;
    }

    public static Response of(String status, List<String> body) {
        return new Response(status, body);
    }

    public static Response notFound() {
        return new Response("404", List.empty());
    }
}
