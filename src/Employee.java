/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

import java.io.Serializable;

/**
 * Class for testing Cache.
 * An employee has number and name, equals method overridden to check number and name equality,
 * implements {@link Serializable} to use standard Java serialization.
 *
 */
public class Employee implements Serializable {

    private int number;
    private String name;

    public Employee(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (number != employee.number) return false;
        if (!name.equals(employee.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}
