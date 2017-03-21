package org.koenighotze.txprototype.user.controller;

import javaslang.Tuple;
import javaslang.collection.Array;
import javaslang.test.Arbitrary;
import javaslang.test.Gen;

/**
 * @author David Schmitz
 */
public class ArbitraryEmail {
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

    public static Arbitrary<String> rfcEmail() {
        Arbitrary<String> arbitraryLocal =
            Arbitrary.string(localPart())
                     .filter(localPart -> !"".equals(localPart))
                     .filter(localPart -> !localPart.startsWith("."))
                     .filter(localPart -> !localPart.endsWith("."))
                     .filter(localPart -> localPart.split("\\.").length <= 1);

        Arbitrary<String> arbitraryDomain =
            Arbitrary.string(domainPart())
                     .filter(domainPart -> !"".equals(domainPart))
                     .filter(domainPart -> !domainPart.startsWith("-"))
                     .filter(domainPart -> !domainPart.endsWith("-"))
                     .filter(domainPart -> !domainPart.startsWith("."))
                     .filter(domainPart -> !domainPart.endsWith("."));

        return arbitraryLocal
            .flatMap(localPart -> arbitraryDomain.map(domain -> localPart + "@" + domain));
    }
}
