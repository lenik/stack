package com.bee32.sem.people.dto;

import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;

public class PersonDto
        extends PartyDto {

    private static final long serialVersionUID = 1L;

    public static final int ROLE_CONTACTS = 256;

    char sex;

    String censusRegister;

    Set<PersonRoleDto> roles;

    public PersonDto() {
        super();
    }

    public PersonDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Party _source) {
        super._marshal(_source);

        Person source = (Person) _source;

        sex = source.getSex() == null ? null : source.getSex().getValue();

        censusRegister = source.getCensusRegister();

        if (selection.contains(ROLES)) {
            int personRoleSelection = 0;
            if (selection.contains(ROLES_CHAIN))
                personRoleSelection = PersonRoleDto.ORG_UNIT_FULL;

            if (selection.contains(ROLE_CONTACTS))
                personRoleSelection |= PersonRoleDto.ORG_CONTACTS;

            roles = new HashSet<PersonRoleDto>();
            for (PersonRole role : source.getRoles()) {
                PersonRoleDto roleDto = marshal(PersonRoleDto.class, personRoleSelection, role);
                roles.add(roleDto);
            }
        }
    }

    @Override
    protected void _unmarshalTo(Party _target) {
        super._unmarshalTo(_target);

        Person target = (Person) _target;

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

    @NLength(max = Person.CENSUS_REGISTER_LENGTH)
    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = TextUtil.normalizeSpace(censusRegister);
    }

    public Set<PersonRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRoleDto> roles) {
        this.roles = roles;
    }

}
