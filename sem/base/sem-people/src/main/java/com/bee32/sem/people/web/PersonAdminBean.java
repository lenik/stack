package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Person;

@ForEntity(Person.class)
public class PersonAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private PersonRoleDto selectedRole;

    public PersonAdminBean() {
        super(Person.class, PersonDto.class, PartyDto.CONTACTS);
    }

    public PersonRoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(PersonRoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<PersonRoleDto> getRoles() {
        PersonDto person = getOpenedObject();
        List<PersonRoleDto> roles = new ArrayList<PersonRoleDto>();

        if (person != null && person.getId() != null) {
            if (person.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(person.getRoles());
            }
        }
        return roles;
    }

}
