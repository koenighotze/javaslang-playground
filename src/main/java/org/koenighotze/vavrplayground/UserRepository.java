package org.koenighotze.vavrplayground;

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
