package org.koenighotze.vavrplayground;

import static io.vavr.control.Try.of;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author David Schmitz
 */
public class ClassicFeedReader {
    private static final String TWITTER = "https://twitter.com/%s";

    public static List<String> getFeed(String user) {
        BufferedReader reader = null;
        List<String> result;
        try {
            URL url = new URL(format(TWITTER, user));
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));

            result = readData(reader);

            reader.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Illegal URL!", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open connection", e);
        }
        finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private static List<String> readData(BufferedReader reader) {
        return reader.lines().collect(toList());
    }

    public static List<String> improved(String user) {
        try {
            URL url = new URL(format(TWITTER, user));
            URLConnection connection = url.openConnection();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return readData(reader);
            }
        } catch (IOException e) {
            return singletonList("No data");
        }
    }


    public static List<String> withVavr(String urlPath) {
        //@formatter:off
        return of(() -> new URL(urlPath))
           .flatMap(url -> of(url::openConnection))
           .flatMap(conn -> of(conn::getInputStream))
           .flatMap(is -> of(() -> new BufferedReader(new InputStreamReader(is))))
           .flatMap(reader -> of(() -> readData(reader)))
           .getOrElse(singletonList("No data"));
        //@formatter:on
    }
    public static void main(String[] args) {
//        System.out.println(getFeed("koenighotze"));
//        System.out.println(improved("koenighotze"));

//        List<String> result = withVavr("notaurl");
        List<String> result = withVavr("http://notthere");
//        List<String> result = withVavr(String.format(TWITTER, "koenighotze"));
        System.out.println(result);
    }
}
