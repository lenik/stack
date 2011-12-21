package com.bee32.sem.hr.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.people.entity.PartyRecord;
import com.bee32.sem.people.entity.Person;

@Entity
public class PersonResume
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    Person person;
    List<PersonSkill> skills = new ArrayList<PersonSkill>();
    PersonEducationType education = PersonEducationType.L2;

    @Transient
    List<PartyRecord> getRecords() {
        return person.getRecords();
    }

}
