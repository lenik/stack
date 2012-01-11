package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartySidTypeDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(Person.class)
public class PersonAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private boolean editable;

    private LazyDataModel<PersonDto> persons;
    private PersonDto selectedPerson;
    private PersonDto person;

    private PersonRoleDto selectedRole;

    public PersonAdminBean() {
        super(Person.class, PersonDto.class, PartyDto.CONTACTS);

        EntityDataModelOptions<Person, PersonDto> options = new EntityDataModelOptions<Person, PersonDto>(//
                Person.class, PersonDto.class, PartyDto.CONTACTS, //
                Order.desc("id"));
        persons = UIHelper.<Person, PersonDto> buildLazyDataModel(options);

        refreshPersonCount();

        editable = false;
    }

    void refreshPersonCount() {
        int count = serviceFor(Person.class).count();
        persons.setRowCount(count);
    }

    void refreshPersonCount(String namePattern) {
        int count = serviceFor(Person.class).count( //
                PeopleCriteria.namedLike(namePattern));
        persons.setRowCount(count);
    }

    @Override
    protected PersonDto getParty() {
        return person;
    }

    @Override
    protected void setParty(PartyDto party) {
        this.person = (PersonDto) party;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public LazyDataModel<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(LazyDataModel<PersonDto> persons) {
        this.persons = persons;
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public boolean isPersonSelected() {
        if (selectedPerson != null)
            return true;
        return false;
    }

    public List<SelectItem> getGenders() {
        List<SelectItem> genders = new ArrayList<SelectItem>();
        for (Gender g : Gender.values())
            genders.add(new SelectItem(g.getValue(), g.getDisplayName()));
        return genders;
    }

    public List<SelectItem> getSidTypes() {
        List<PartySidType> sidTypes = serviceFor(PartySidType.class).list();
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
        List<PersonRoleDto> roles = new ArrayList<PersonRoleDto>();

        if (person != null && person.getId() != null) {
            if (person.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(person.getRoles());
            }
        }

        return roles;
    }

    public void doNew() {
        person = new PersonDto().create();

        setActiveTab(TAB_FORM);
        editable = true;
    }

    public void doModify() {
        if (selectedPerson == null) {
            uiLogger.error("请选择需要修改的联系人!");
            return;
        }

        person = reload(selectedPerson, -1);

        setActiveTab(TAB_FORM);
        editable = true;
    }

    public void doDelete() {
        if (selectedPerson == null) {
            uiLogger.error("请选择需要删除的联系人!");
            return;
        }

        try {
            Person person = serviceFor(Person.class).getOrFail(selectedPerson.getId());
            person.getContacts().clear();
            serviceFor(Person.class).save(person);
            serviceFor(Person.class).delete(person);
            refreshPersonCount();

            selectedPerson = null;

        } catch (Exception e) {
            uiLogger.error("删除联系人失败", e);
        }
    }

    public void doSave() {
        try {
            Person _person = (Person) person.unmarshal();
            serviceFor(Person.class).saveOrUpdate(_person);
            refreshPersonCount();

            setActiveTab(TAB_INDEX);
            editable = false;
            uiLogger.info("联系人保存成功");
        } catch (Exception e) {
            uiLogger.error("联系人保存失败", e);
        }
    }

    public void doCancel() {
        setActiveTab(TAB_INDEX);
        editable = false;
        person = null;
    }

    public void doDetail() {
        if (selectedPerson == null) {
            uiLogger.error("请选择需要查看详细信息的联系人!");
            return;
        }
        person = reload(selectedPerson, -1);
        setActiveTab(TAB_FORM);
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public void onRowSelectRole(SelectEvent event) {
    }

    public void onRowUnselectRole(UnselectEvent event) {
    }

    @Override
    public void find() {
        EntityDataModelOptions<Person, PersonDto> options = new EntityDataModelOptions<Person, PersonDto>(//
                Person.class, PersonDto.class, PartyDto.CONTACTS, //
                PeopleCriteria.namedLike(namePattern), //
                Order.desc("id"));
        persons = UIHelper.<Person, PersonDto> buildLazyDataModel(options);

        refreshPersonCount(namePattern);
    }

}
