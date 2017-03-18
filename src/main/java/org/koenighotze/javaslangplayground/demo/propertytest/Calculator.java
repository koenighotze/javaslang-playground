package org.koenighotze.javaslangplayground.demo.propertytest;

import static java.lang.String.format;
import static java.lang.System.out;

/**
 * @author David Schmitz
 */
public class Calculator {
    public int add(int a, int b) {
//        if (a == 2 && b == 2) {
//            return 4;
//        }
//
//        return 7;
        out.println(format("Adding %d and %d", a, b));
        return a + b;
    }
}
