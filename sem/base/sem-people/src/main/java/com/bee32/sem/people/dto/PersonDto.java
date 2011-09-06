package com.bee32.sem.people.dto;

import java.util.HashSet;
import java.util.Set;

import com.bee32.sem.people.Gender;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;

public class PersonDto
        extends AbstractPartyDto<Person> {

    private static final long serialVersionUID = 1L;

    char sex;

    String censusRegister;

    Set<PersonRoleDto> roles;

    public PersonDto() {
        super();
    }

    public PersonDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Person source) {
        super._marshal(source);

        sex = source.getSex() == null ? null : source.getSex().getValue();

        censusRegister = source.getCensusRegister();

        if (selection.contains(ROLES)) {
            int personRoleSelection = 0;
            if (selection.contains(ROLES_CHAIN))
                personRoleSelection = PersonRoleDto.ORG_UNIT_FULL;

            roles = new HashSet<PersonRoleDto>();
            for (PersonRole role : source.getRoles()) {
                PersonRoleDto roleDto = marshal(PersonRoleDto.class, personRoleSelection, role);
                roles.add(roleDto);
            }
        }
    }

    @Override
    protected void _unmarshalTo(Person target) {
        super._unmarshalTo(target);

        target.setSex(Gender.valueOf(sex));

        target.setCensusRegister(censusRegister);

        if (selection.contains(ROLES))
            mergeSet(target, "roles", roles);
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getSexText() {
        return Gender.valueOf(sex).getDisplayName();
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public Set<PersonRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRoleDto> roles) {
        this.roles = roles;
    }

}
