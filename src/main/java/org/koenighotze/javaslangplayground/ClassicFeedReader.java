package org.koenighotze.javaslangplayground;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static javaslang.control.Try.of;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Schmitz
 */
public class ClassicFeedReader {
    private static final String TWITTER = "https://twitter.com/%s";

    public static List<String> getFeed(String user) {
        BufferedReader reader = null;
        List<String> result = new ArrayList<>();
        try {
            URL url = new URL(String.format(TWITTER, user));
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
            URL url = new URL(String.format(TWITTER, user));
            URLConnection connection = url.openConnection();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return readData(reader);
            }
        } catch (IOException e) {
            return singletonList("No data");
        }
    }


    public static List<String> withJavaslang(String urlPath) {
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

//        List<String> result = withJavaslang("notaurl");
        List<String> result = withJavaslang("http://notthere");
//        List<String> result = withJavaslang(String.format(TWITTER, "koenighotze"));
        System.out.println(result);
    }
}
