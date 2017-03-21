package org.koenighotze.javaslangplayground.demo.treedemo;

import static java.lang.System.out;
import static javaslang.render.text.PrettyPrinter.pp;

import javaslang.collection.TreeSet;

public class TreeDemo {

    private static void demo() {
        TreeSet<String> tree = TreeSet.of("C3PO", "R2D2", "K2SO");
        TreeSet<String> tree2 = tree.add("Chopper");

        out.println(pp(tree.toTree()));
        out.println(pp(tree2.toTree()));
    }

    public static void main(String[] args) {
        TreeDemo.demo();
    }
}
