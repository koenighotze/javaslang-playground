package org.koenighotze.txprototype.user.controller;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static javaslang.test.Gen.choose;
import static org.koenighotze.txprototype.user.controller.ArbitraryEmail.rfcEmail;

import java.util.Random;
import java.util.regex.Pattern;

import javaslang.test.Arbitrary;
import javaslang.test.Gen;
import javaslang.test.Property;
import org.junit.Test;

/**
 * @author David Schmitz
 */
public class FooTest {
    @Test
    public void gen() {
        for (int k = 0; k < 1000; k++) {
            Random random = new Random();
            int i = random.nextInt();
            System.out.println("CHAR IS: " + Character.toString((char) i));
        }
    }

    @Test
    public void simple() {
        Property.def("Should not go boom")
                .forAll(Arbitrary.string(choose('a', 'z')).filter(s -> s.length() > 0))
                .suchThat((String email) -> email.length() > 0)
                .check()
                .assertIsSatisfied();
    }

    private static Arbitrary<String> email(Gen<Character> gen) {
        return size -> random -> genRandomString(gen, size, random) + "@" + genRandomString(gen, size,
            random) + "." + genRandomString(gen, size, random);
    }

    private static String genRandomString(Gen<Character> gen, int size, Random random) {
        return choose(0, size)
                  .map(i -> {
                      final char[] chars = new char[i];
                      for (int j = 0; j < i; j++) {
                          chars[j] = gen.apply(random);
                      }
                      return new String(chars);
                  })
                  .apply(random);
    }


    private static Arbitrary<String> email2(Gen<Character> gen) {
        Arbitrary<String> a = ((int size) -> (Random random) -> genRandomString(gen, size, random));
        return a.flatMap(localPart -> (int size) -> (Random random) -> localPart + "@" + genRandomString(gen, size,
            random) + ".de");
    }

    @Test
    public void composing() {
        Property.def("Should not go boom")
                .forAll(email(choose('a', 'z')))
                .suchThat((String email) -> email.length() > 0)
                .check()
                .assertIsSatisfied();
    }

    @Test
    public void validate_email() {
        final Pattern NAIVE_EMAIL = compile("^[!#$%&'*+-/=?^_`{|}~A-Z0-9.+-]+@[A-Z0-9.-]+$", CASE_INSENSITIVE);
        Property.def("Should not go boom")
                .forAll(rfcEmail())
                .suchThat((String email) -> NAIVE_EMAIL.matcher(email).matches())
                .check()
                .assertIsSatisfied();
    }

}
