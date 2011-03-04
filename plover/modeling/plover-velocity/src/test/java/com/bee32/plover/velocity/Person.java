package com.bee32.plover.velocity;

public class Person {

    private String name;
    private int age;
    private boolean isFemale;
    private String loc;

    public Person(String name, int age, boolean isFemale, String location) {
        this.name = name;
        this.age = age;
        this.isFemale = isFemale;
        this.loc = location;
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

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public String getLocation() {
        return loc;
    }

    public void setLocation(String location) {
        this.loc = location;
    }

}
