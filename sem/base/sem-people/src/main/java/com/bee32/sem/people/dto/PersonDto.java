package com.bee32.sem.people.dto;

import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.arch.util.MarshalType;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.entity.PersonSidType;

public class PersonDto
        extends AbstractPartyDto<Person> {

    private static final long serialVersionUID = 1L;

    Character sex;

    String censusRegister;
    PersonSidTypeDto sidType;

    Set<PersonRoleDto> roles;

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

        sex = source.getSex() == null? null : source.getSex().getValue();

        censusRegister = source.getCensusRegister();

        PersonSidType _sidType = source.getSidType();
        sidType = new PersonSidTypeDto(_sidType);
        sidType.marshalAs(MarshalType.ID_REF);

        roles =  new HashSet<PersonRoleDto>();
        for(PersonRole role : source.getRoles()) {
            PersonRoleDto roleDto = new PersonRoleDto(role);
            roles.add(roleDto);
        }
    }

	@Override
	protected void _unmarshalTo(Person target) {
		super._unmarshalTo(target);

		target.setSex(Gender.valueOf(sex));

        target.setCensusRegister(censusRegister);

		// XXX - Should remove this later.
		String sidTypeId = sidType.getId();
		if (sidTypeId != null && sidTypeId.isEmpty())
		    sidTypeId = null;
		sidType.setId(sidTypeId);
		sidType.marshalAs(MarshalType.ID_REF);
		merge(target, "sidType", sidType);

		mergeSet(target, "roles", roles);
	}


    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public String getSexText() {
        if(sex == null)
            return null;
        return Gender.valueOf(sex).getDisplayName();
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

}
