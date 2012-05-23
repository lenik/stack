package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.search.SearchFragment;
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

    void attributeFilter(String pattern) {
        AttributeSearchFragment asf = new AttributeSearchFragment(pattern);
        List<AttributeSearchFragment> asfs = new ArrayList<AttributeSearchFragment>();
        for (SearchFragment sf : getSearchFragments()) {
            if (sf instanceof AttributeSearchFragment)
                asfs.add((AttributeSearchFragment) sf);
        }

        if (!asfs.contains(asf)) {
            addSearchFragment(asf);
        }
    }

    public void addEmployeeRestriction() {
        attributeFilter("是内部员工");
    }

    public void addCustomerRestriction() {
        attributeFilter("是客户");
    }

    public void addSupplierRestriction() {
        attributeFilter("是供应商");
    }

    public void addNumberRestricion() {
        if (searchPattern == null || searchPattern.isEmpty())
            return;

        List<SearchFragment> searchFragmentList = getSearchFragments();

        ContactSearchFragment csf = null;

        for (SearchFragment searchFragment : searchFragmentList) {
            if (searchFragment instanceof ContactSearchFragment) {
                csf = (ContactSearchFragment) searchFragment;
            }
        }
        if (csf == null) {
            csf = new ContactSearchFragment(searchPattern);
            addSearchFragment(csf);
        } else {
            csf.pattern = searchPattern;
            searchFragmentsChanged();
        }
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
