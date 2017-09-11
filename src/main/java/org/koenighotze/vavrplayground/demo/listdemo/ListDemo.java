package org.koenighotze.vavrplayground.demo.listdemo;

import io.vavr.collection.List;

/**
 * @author David Schmitz
 */
public class ListDemo {

    private static void demo() {
        List<String> fighter = List.of("Han", "Luke");

        System.out.println(fighter);

        List<String> more = fighter.prepend("Ben");
        System.out.println(more);
    }

    public static void main(String[] args) {
        ListDemo.demo();
    }
}
