package org.koenighotze.vavrplayground.demo.demo4;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import org.junit.Test;

/**
 * @author David Schmitz
 */
public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    public void two_and_two_is_four() {
        assertThat(calculator.add(2, 2)).isEqualTo(4);
    }

    @Test
    public void two_and_five_is_seven() {
        assertThat(calculator.add(2, 5)).isEqualTo(7);
        // tdd gone cul-de-sac
    }

    @Test
    public void property_check() {
        Property.def("Add works as god intended")
                .forAll(Arbitrary.integer(), Arbitrary.integer())
                .suchThat((a, b) -> calculator.add(a, b) ==  a + b)
                .check()
                .assertIsSatisfied();
    }

    @Test
    public void adding_zero_is_unit_op() {
        Property.def("Add zero it the unit")
                .forAll(Arbitrary.integer())
                .suchThat((a) -> calculator.add(a, 0) ==  a)
                .check(1000, 50)
                .assertIsSatisfied();
    }
}
