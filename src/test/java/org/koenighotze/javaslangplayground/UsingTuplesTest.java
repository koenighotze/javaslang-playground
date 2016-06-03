package org.koenighotze.javaslangplayground;

import static javaslang.Tuple.of;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import javaslang.Tuple2;

/**
 * @author dschmitz
 */
public class UsingTuplesTest {

    @Test
    public void tuples_playground() {
        Tuple2<String, Integer> tuple = of("FileReader", 1);
        assertThat(tuple._1).isEqualTo("FileReader");

        assertThat(tuple.arity()).isEqualTo(2);
    }

    @Test
    public void tuples_map() {
        Tuple2<String, Integer> tuple = of("Foo", 1);

        Tuple2<String, Integer> results = tuple.map((s, i) -> of(s + "Bar", i + 5));

        assertThat(results._1).isEqualTo("FooBar");
        assertThat(results._2).isEqualTo(6);

    }

}
