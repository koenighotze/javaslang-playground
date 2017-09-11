package org.koenighotze.vavrplayground;

import java.time.*;

/**
 * @author dschmitz
 */
final class Subject {
    private final String name;
    private final LocalDate birthday;
    private Integer value;

    public Subject(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Subject withBirthday(LocalDate newBirthday) {
        return new Subject(name, newBirthday);
    }

    public Subject withName(String newName) {
        return new Subject(newName, birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        return !(birthday != null ? !birthday.equals(subject.birthday) : subject.birthday != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public boolean isValid() {
        // obviously this is stupid and only serves trying out the lift api
        if (null == name) {
            throw new IllegalStateException("Name must not be null and I don't know about preconditions :(");
        }

        if (null == birthday) {
            throw new IllegalStateException("Birthday must not be null and I don't know about preconditions :(");
        }

        if (name.length() > 10) {
            return false;
        }

        return true;
    }

    public Integer callMeTwiceAndIfWillFail() {
        if (value == null) {
            value = Integer.MAX_VALUE;
            return value;
        }

        throw new RuntimeException("Thou must not call me twice");
    }
}
