package org.koenighotze.vavrplayground;

import java.util.Objects;

/**
 * @author David Schmitz
 */
public class Team {
    private final String name;

    private Team(String name) {
        this.name = name;
    }

    public static Team withName(String name) {
        return new Team(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Team{" +
            "name='" + name + '\'' +
            '}';
    }
}
