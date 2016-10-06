package org.koenighotze.javaslangplayground;

import javaslang.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;
import static org.koenighotze.javaslangplayground.Team.withName;

/**
 * @author David Schmitz
 */
public class LinkedListTest {

    @Test
    public void a_linked_list_is_persistent() {
        List<Team> teams = List.of(withName("F95"));
        List<Team> moreTeams = teams.append(withName("FCK"));

        assertNotSame(teams, moreTeams);
        assertThat(teams.size()).isEqualTo(1);
        assertThat(teams.get(0)).isSameAs(moreTeams.get(0));
    }

}
