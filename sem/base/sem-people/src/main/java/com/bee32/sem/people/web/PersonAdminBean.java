package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

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

    public void addEmployeeRestriction() {
        setSearchFragment("attribute", "是内部员工", PeopleCriteria.isEmployee());
    }

    public void addCustomerRestriction() {
        setSearchFragment("attribute", "是客户", PeopleCriteria.isCustomer());
    }

    public void addSupplierRestriction() {
        setSearchFragment("attribute", "是供应商", PeopleCriteria.isSupplier());
    }

    public void addNumberRestricion() {
        if (searchPattern == null || searchPattern.isEmpty())
            return;

        setSearchFragment("number", "联系方式包含 " + searchPattern,//
                PeopleCriteria.withAnyNumberLike(searchPattern));

        searchPattern = null;
    }

    public List<PersonRoleDto> getRoles() {
        PersonDto person = getOpenedObject();
        person = reload(person, PartyDto.CONTACTS);
        List<PersonRoleDto> roles = new ArrayList<PersonRoleDto>();

        if (person != null && person.getId() != null) {
            if (person.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(person.getRoles());
            }
        }
        return roles;
    }

}
