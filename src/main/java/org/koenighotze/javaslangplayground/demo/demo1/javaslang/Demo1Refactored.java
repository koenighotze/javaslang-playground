package org.koenighotze.javaslangplayground.demo.demo1.javaslang;

import javaslang.collection.List;
import javaslang.control.Try;
import org.koenighotze.javaslangplayground.User;

public class Demo1Refactored {
    public List<User> filterValidUsers(List<User> users) {
        return
            users
                .filter(user -> Try.of(user::validate)
                                   .getOrElse(false));
    }
}
