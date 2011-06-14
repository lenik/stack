package com.bee32.sem.people.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.util.MarshalType;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.entity.PersonSidType;

public class PersonDto
        extends AbstractPartyDto<Person> {

    private static final long serialVersionUID = 1L;

    Integer sex;

    String censusRegister;
    PersonSidTypeDto sidType;

    Set<PersonRoleDto> roles;

    List<PersonContactDto> contacts;

    public PersonDto() {
        super();
    }

    public PersonDto(Person source) {
        super(source);
    }

    public PersonDto(int selection) {
        super(selection);
    }

    public PersonDto(int selection, Person source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(Person source) {
        super._marshal(source);

        sex = source.getSex() == null? null : source.getSex().ordinal();

        censusRegister = source.getCensusRegister();

        PersonSidType _sidType = source.getSidType();
        sidType = new PersonSidTypeDto(_sidType);
        sidType.marshalAs(MarshalType.ID_REF);

        roles =  new HashSet<PersonRoleDto>();
        for(PersonRole role : source.getRoles()) {
            PersonRoleDto roleDto = new PersonRoleDto(role);
            roles.add(roleDto);
        }

        contacts = marshalList(PersonContactDto.class, source.getContacts());
    }

	@Override
	protected void _unmarshalTo(Person target) {
		super._unmarshalTo(target);

		target.setSex(Gender.values()[sex]);

		target.setCensusRegister(censusRegister);

		// XXX - Should remove this later.
		sidType.marshalAs(MarshalType.ID_REF);
		sidType.setName(sidType.getId());

		merge(target, "sidType", sidType);

		mergeSet(target, "roles", roles);
		mergeList(target, "contacts", contacts);
	}


    public Integer getSex() {
        return sex;
    }

    public String getSexText() {
        if(sex == null)
            return null;
        return Gender.values()[sex].toString();
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public PersonSidTypeDto getSidType() {
        return sidType;
    }

    public void setSidType(PersonSidTypeDto sidType) {
        this.sidType = sidType;
    }

    public Set<PersonRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRoleDto> roles) {
        this.roles = roles;
    }

    public List<PersonContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<PersonContactDto> contacts) {
        this.contacts = contacts;
    }



}
