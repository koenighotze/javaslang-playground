package org.koenighotze.javaslangplayground.demo.collection;

import javaslang.collection.List;
import org.koenighotze.javaslangplayground.User;
import org.koenighotze.javaslangplayground.demo.collection.jdk8.UserRepo;

/**
 * Going from Javaslang to Java and back again
 * @author David Schmitz
 */
public class Interop {
    public List<User> findUserByName() {
        return List.ofAll(
                new UserRepo().findAllUsers() // jdk8 list
               );
    }

}
