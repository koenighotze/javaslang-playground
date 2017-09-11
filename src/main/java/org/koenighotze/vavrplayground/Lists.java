package org.koenighotze.vavrplayground;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

public class Lists {
    public static <S, T> List<T> map(List<S> first, Function<S, T> f) {
        return first.stream().map(f).collect(toList());
    }
}
