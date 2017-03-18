package org.koenighotze.javaslangplayground.demo.intro.javaslang;

import javaslang.collection.List;
import javaslang.control.Try;
import org.koenighotze.javaslangplayground.User;

/**
 * @author David Schmitz
 */
public class Demo1Refactored {

    public List<User> filterValidUsers(java.util.List<User> users) {
        return
            List.ofAll(users)
                .filter(user -> Try.of(user::validate)
                                   .getOrElse(false));
    }
}
