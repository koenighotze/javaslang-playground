package org.koenighotze.txprototype.user.controller;

import static javaslang.test.Arbitrary.string;
import static javaslang.test.Gen.choose;

import javaslang.*;
import javaslang.collection.*;
import javaslang.test.*;

/**
 * @author David Schmitz
 */
public class ArbitraryData {
    private static Gen<Character> localPart() {
        Array<Character> characters = Array.ofAll("!#$%&'*+-/=?^_`{|}~".toCharArray());

        return Gen.frequency(
            Tuple.of(1, Gen.choose('a', 'z')),
            Tuple.of(1, Gen.choose('0', '9')),
            Tuple.of(1, Gen.choose('A', 'Z')),
            Tuple.of(1, Gen.choose(characters)),
            Tuple.of(1, Gen.of('.'))
        );
    }

    private static Gen<Character> domainPart() {
        return Gen.frequency(
            Tuple.of(1, Gen.choose('a', 'z')),
            Tuple.of(1, Gen.choose('A', 'Z')),
            Tuple.of(1, Gen.choose('0', '9')),
            Tuple.of(1, Gen.of('-')),
            Tuple.of(1, Gen.of('.'))
        );
    }

    public static javaslang.test.Arbitrary<String> rfcEmail() {
        javaslang.test.Arbitrary<String> arbitraryLocal =
            javaslang.test.Arbitrary.string(localPart())
                                    .filter(localPart -> !"".equals(localPart))
                                    .filter(localPart -> !localPart.startsWith("."))
                                    .filter(localPart -> !localPart.endsWith("."))
                                    .filter(localPart -> localPart.split("\\.").length <= 1);

        javaslang.test.Arbitrary<String> arbitraryDomain =
            javaslang.test.Arbitrary.string(domainPart())
                                    .filter(domainPart -> !"".equals(domainPart))
                                    .filter(domainPart -> !domainPart.startsWith("-"))
                                    .filter(domainPart -> !domainPart.endsWith("-"))
                                    .filter(domainPart -> !domainPart.startsWith("."))
                                    .filter(domainPart -> !domainPart.endsWith("."));

        return arbitraryLocal
            .flatMap(localPart -> arbitraryDomain.map(domain -> localPart + "@" + domain));
    }

    public static javaslang.test.Arbitrary<String> arbitraryNick() {
        return string(choose('a', 'z'));
    }

    public static javaslang.test.Arbitrary<String> arbitraryUnicodeString() {
        Gen<Character> randomUnicode = random -> (char) random.nextInt();
        return string(randomUnicode);
    }
}
