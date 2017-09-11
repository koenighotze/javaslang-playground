package org.koenighotze.vavrplayground;

import io.vavr.control.Option;

/**
 * @author David Schmitz
 */
public class User {
    private Option<Address> address = Option.none();

    public Option<Address> getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = Option.of(address);
    }

    public boolean validateAddress() {
        return
          getAddress()
            .map(Address::getStreet)
            .map(street -> street.matches("^\\w+$"))
            .getOrElseThrow(() -> new IllegalStateException("Invalid street " + getAddress()));
    }

    public boolean validate() throws Exception {
        return false;
    }
}
