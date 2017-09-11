package org.koenighotze.vavrplayground.demo.demo2.java8;

import org.koenighotze.vavrplayground.Address;
import org.koenighotze.vavrplayground.plain.User;
import org.koenighotze.vavrplayground.plain.UserRepository;

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
