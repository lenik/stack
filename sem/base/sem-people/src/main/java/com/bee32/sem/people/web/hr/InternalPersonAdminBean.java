package com.bee32.sem.people.web.hr;

import org.primefaces.model.LazyDataModel;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(Person.class)
public class InternalPersonAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private LazyDataModel<PersonDto> persons;

    public InternalPersonAdminBean() {
        EntityDataModelOptions<Person, PersonDto> options = new EntityDataModelOptions<Person, PersonDto>(//
                Person.class, PersonDto.class, 0, //
                Order.desc("id"), //
                EntityCriteria.ownedByCurrentUser(), //
                PeopleCriteria.internal());
        persons = UIHelper.buildLazyDataModel(options);

        refreshPersonCount();

    }

    void refreshPersonCount() {
        int count = serviceFor(Person.class).count(EntityCriteria.ownedByCurrentUser(), PeopleCriteria.internal());
        persons.setRowCount(count);
    }

    public LazyDataModel<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(LazyDataModel<PersonDto> persons) {
        this.persons = persons;
    }

}
