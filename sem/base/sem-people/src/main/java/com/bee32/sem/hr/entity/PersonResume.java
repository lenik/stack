package com.bee32.sem.hr.entity;

import java.util.List;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.people.entity.Person;

@Entity
public class PersonResume
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    Person person;

    List<PartyRecord> records;
    List<PersonSkill> skills;

    PersonEducationType education;

}
