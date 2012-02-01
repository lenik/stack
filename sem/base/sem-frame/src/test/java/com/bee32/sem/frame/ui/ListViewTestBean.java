package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.faces.view.ViewBean;

public class ListViewTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    List<Person> persons;
    ListMBean<Person> personsMBean = ListMBean.fromEL(this, "persons", Person.class);

    public ListViewTestBean() {
        persons = new ArrayList<>();
        persons.add(new Person("lenik", 31, false));
        persons.add(new Person("lily", 18, true));
    }

    public List<Person> getPersons() {
        return persons;
    }

    public ListMBean<Person> getPersonsMBean() {
        return personsMBean;
    }

}
