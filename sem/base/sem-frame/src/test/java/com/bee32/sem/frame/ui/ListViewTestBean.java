package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;

import javax.free.Person;

import org.apache.myfaces.view.facelets.tag.composite.InsertFacetHandler;
import org.apache.myfaces.view.facelets.tag.jsf.core.FacetHandler;

import com.bee32.plover.faces.view.ViewBean;

public class ListViewTestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    List<Person> persons;

    public ListViewTestBean() {
        persons = new ArrayList<>();
        persons.add(new Person("lenik", 31, false));
        persons.add(new Person("lily", 18, true));
    }

    public ListModel<Person> getPersonModel() {
        return new ListModel<Person>(persons, Person.class);
    }

    {
        InsertFacetHandler ifh;
        FacetHandler ffh;
    }
}
