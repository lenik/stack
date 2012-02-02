package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.faces.view.ViewBean;

public class ListViewTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    List<Person> persons;
    List<Person> lilyChildren;
    ListMBean<Person> personsMBean = ListMBean.fromEL(this, "persons", Person.class);
    ListMBean<Person> childrenMBean = ListMBean.fromEL(this, "personsMBean.copy.children", Person.class);

    // ListMBean<Person> childrenMBean = ListMBean.fromEL(this, "lilyChildren", Person.class);

    public ListViewTestBean() {
        Person lenik = new Person("lenik", 31, false);
        Person lily = new Person("lily", 18, true);
        Person lucy = new Person("lucy", 5, true);
        Person jay = new Person("Jay-Z", 35, false);

        persons = new ArrayList<>();
        persons.add(lenik);
        persons.add(lily);

        lilyChildren = new ArrayList<Person>();
        lilyChildren.add(lucy);
        lilyChildren.add(jay);
        lily.setChildren(lilyChildren);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Person> getLilyChildren() {
        return lilyChildren;
    }

    public ListMBean<Person> getPersonsMBean() {
        return personsMBean;
    }

    public ListMBean<Person> getChildrenMBean() {
        return childrenMBean;
    }

}
