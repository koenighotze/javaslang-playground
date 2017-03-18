package org.koenighotze.javaslangplayground.demo.intro.java8;

import java.util.List;
import java.util.stream.Collectors;

import org.koenighotze.javaslangplayground.User;

/**
 * @author David Schmitz
 */
public class Demo1 {

    public List<User> filterValidUsers(List<User> users) {
        return
            users.stream()
             .filter(user -> {
                 try {
                     return user.validate();
                 } catch (Exception ex) {
                     return false;
                 }})
             .collect(Collectors.toList());
    }


}
