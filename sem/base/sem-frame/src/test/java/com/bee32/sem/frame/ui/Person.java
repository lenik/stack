package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.EntitySpec;
import com.bee32.plover.util.TextUtil;

public class Person
        extends EntitySpec<String> {

    private static final long serialVersionUID = 1L;

    String name;
    int age;
    boolean girl;
    List<Person> children = new ArrayList<Person>();

    public Person() {
    }

    public Person(String name, int age, boolean girl) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Person)
            _populate((Person) source);
        else
            super.populate(source);
    }

    protected void _populate(Person o) {
        super._populate(o);
        name = o.name;
        age = o.age;
        girl = o.girl;
        children = new ArrayList<Person>(o.children);
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @NLength(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = TextUtil.normalizeSpace(name);
    }

    @Min(5)
    @Max(100)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    public int getChildrenCount() {
        return children.size();
    }

}
