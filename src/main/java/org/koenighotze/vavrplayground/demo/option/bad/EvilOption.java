package org.koenighotze.vavrplayground.demo.option.bad;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author David Schmitz
 */
public class EvilOption {
    // use option as a null wrapper
    public BigDecimal calculateTaxA(BigDecimal amount, BigDecimal percentage) {
        return Optional.ofNullable(percentage)
                       .map(amount::multiply)
                       .get();
    }

    // option isPresent ->
    public BigDecimal calculateTaxB(BigDecimal amount, Optional<BigDecimal> percentage) {
        if (percentage.isPresent()) {
            return percentage.get()
                             .multiply(amount);
        }
        return amount;
    }
}
