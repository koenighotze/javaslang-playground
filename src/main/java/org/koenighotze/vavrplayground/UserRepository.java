package org.koenighotze.javaslangplayground;

import io.vavr.control.Option;

/**
 * @author David Schmitz
 */
public class UserRepository {

    public Option<User> findOne(String id) {
        // irrelevant
        return Option.none();
    }
}
