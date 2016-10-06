package org.koenighotze.javaslangplayground;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Queue;
import javaslang.control.Option;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.koenighotze.javaslangplayground.Team.withName;

/**
 * @author David Schmitz
 */
public class QueueTest {
    @Test
    public void dequeue() {
        Queue<Team> t = Queue.of(withName("F95"), withName("FCK"));

        Tuple2<Team, Queue<Team>> t2 = t.dequeue();
        assertThat(withName("F95")).isEqualTo(t2._1);
        assertThat(t.tail().head()).isSameAs(t2._2.head());
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeue_empty_queue() {
        Queue<Team> t = Queue.empty();
        t.dequeue();
    }

    @Test
    public void dequeue_empty_queue_functional_way() {
        Queue<Team> t = Queue.empty();
        Option<Tuple2<Team, Queue<Team>>> t2 = t.dequeueOption();
        assertThat(t2.isDefined()).isFalse();
    }

    @Test
    public void dequeue_option_style() {
        Queue<String> t = Queue.empty();
        //@formatter:off
        String result =
            t.dequeueOption()
             .getOrElse(Tuple.of("nothing", Queue.empty()))
             ._1;
        //@formatter:on
        assertThat(result).isEqualTo("nothing");
    }
}
