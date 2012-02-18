package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartySidTypeDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(Person.class)
public class PersonAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private PersonRoleDto selectedRole;

    public PersonAdminBean() {
        super(Person.class, PersonDto.class, PartyDto.CONTACTS);
    }

    public List<SelectItem> getGenders() {
        List<SelectItem> genders = new ArrayList<SelectItem>();
        for (Gender g : Gender.values())
            genders.add(new SelectItem(g.getValue(), g.getDisplayName()));
        return genders;
    }

    public List<SelectItem> getSidTypes() {
        List<PartySidType> sidTypes = ctx.data.access(PartySidType.class).list();
        List<PartySidTypeDto> sidTypeDtos = DTOs.marshalList(PartySidTypeDto.class, sidTypes);
        return UIHelper.selectItemsFromDict(sidTypeDtos);
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
