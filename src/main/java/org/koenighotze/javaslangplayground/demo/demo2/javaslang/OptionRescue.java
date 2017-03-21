package org.koenighotze.javaslangplayground.demo.demo2.javaslang;

import org.koenighotze.javaslangplayground.Address;
import org.koenighotze.javaslangplayground.User;
import org.koenighotze.javaslangplayground.UserRepository;

public class OptionRescue {
    private static String fetchStreetForUser(String id) {
        UserRepository repo = new UserRepository();
        return
            repo
             .findOne(id)
             .flatMap(User::getAddress)
             .map(Address::getStreet)
             .getOrElse("");
    }
}
