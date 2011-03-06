package com.bee32.plover.arch.util;

import java.io.Serializable;

public class PersonBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private char gender;

    public PersonBean() {
    }

    public PersonBean(String name, char gender, int age) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + gender;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonBean other = (PersonBean) obj;
        if (age != other.age)
            return false;
        if (gender != other.gender)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + "/" + gender + "/" + age;
    }

    public static final PersonBean Mike = new PersonBean("Mike", 'm', 17);
    public static final PersonBean Milk = new PersonBean("Milk", 'f', 13);

}
