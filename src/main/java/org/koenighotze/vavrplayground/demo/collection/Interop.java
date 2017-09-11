package org.koenighotze.vavrplayground.demo.collection;

import io.vavr.collection.List;
import org.koenighotze.vavrplayground.User;
import org.koenighotze.vavrplayground.demo.collection.jdk8.UserRepo;

/**
 * Going from Vavr to Java and back again
 * @author David Schmitz
 */
public class Interop {
    public List<User> findUserByName() {
        return List.ofAll(
                new UserRepo().findAllUsers() // java8 list
               );
    }

}
