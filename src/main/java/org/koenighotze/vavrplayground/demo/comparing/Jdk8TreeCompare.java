package org.koenighotze.vavrplayground.demo.comparing;

import static java.util.Arrays.asList;

import java.util.TreeSet;

/**
 * @author David Schmitz
 */
public class Jdk8TreeCompare {

    private static void demo() {
        TreeSet<String> first = new java.util.TreeSet<>();
        first.addAll(asList("Han", "Luke", "Chewie"));

        TreeSet<String> second = first;
        first.addAll(asList("Han", "Luke", "Chewie"));
        second.add("Ezra");

        System.out.println(first.equals(second));
    }

    public static void main(String[] args) {
        Jdk8TreeCompare.demo();
    }
}
