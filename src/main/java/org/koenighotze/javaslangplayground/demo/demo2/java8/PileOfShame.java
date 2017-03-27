package org.koenighotze.javaslangplayground.demo.demo2.jdk8;

import org.koenighotze.javaslangplayground.Address;
import org.koenighotze.javaslangplayground.plain.User;
import org.koenighotze.javaslangplayground.plain.UserRepository;

/**
 * @author David Schmitz
 */
public class PileOfShame {
    private static String fetchStreetForUser(String id) {
        UserRepository repo = new UserRepository();
        User user = repo.findOne(id);
        if (user != null) {
            Address address = user.getAddress();
            if (null != address) {
                return address.getStreet();
            }
        }
        return null;
    }
}
