package org.koenighotze.vavrplayground;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotSame;
import static org.koenighotze.vavrplayground.Team.withName;

import io.vavr.collection.*;
import org.junit.*;

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
