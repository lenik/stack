package com.bee32.sem.chance.web;

import java.io.Serializable;
import java.util.List;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.people.web.ChoosePartyDialogBean;

public class ChooseSuggesterDialogBean
        extends ChoosePartyDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseSuggesterDialogBean() {
        super(Person.class, PersonDto.class, 0);
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseSuggesterDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        elements.add(PeopleCriteria.internal());
    }
}
