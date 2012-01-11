package com.bee32.sem.hr.web;

import org.primefaces.model.LazyDataModel;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public class InternalPersonAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private LazyDataModel<PersonDto> persons;

    PersonDto selectedPerson;

    public InternalPersonAdminBean() {
        EntityDataModelOptions<Person, PersonDto> options = new EntityDataModelOptions<Person, PersonDto>(//
                Person.class, PersonDto.class, 0, //
                Order.desc("id"),
                PeopleCriteria.internal());
        persons = UIHelper.buildLazyDataModel(options);

        refreshPersonCount();

    }

    void refreshPersonCount() {
        int count = serviceFor(Person.class).count(
                PeopleCriteria.internal());
        persons.setRowCount(count);
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


}
