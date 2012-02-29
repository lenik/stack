package com.bee32.sem.people.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Disjunction;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.misc.ChooseEntityDialogBean;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;

public class ChoosePartyDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePartyDialogBean.class);

    Boolean employee;
    Boolean customer;
    Boolean supplier;
    Integer tagId;

    public ChoosePartyDialogBean() {
        this(Party.class, PartyDto.class, 0);
        // addSearchFragment("类型为", User.class);
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChoosePartyDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        List<CriteriaElement> types = new ArrayList<CriteriaElement>();
        {
            if (employee != null)
                types.add(new Equals("employee", employee));
            if (customer != null)
                types.add(new Equals("customer", customer));
            if (supplier != null)
                types.add(new Equals("supplier", supplier));
            if (!types.isEmpty())
                elements.add(new Disjunction(types.toArray(new CriteriaElement[0])));
        }
        if (tagId != null) {
            elements.add(new Alias("tags", "tag"));
            elements.add(new Equals("tag.id", tagId));
        }
    }

    // Properties

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
        refreshRowCount();
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
        refreshRowCount();
    }

    public Boolean getSupplier() {
        return supplier;
    }

    public void setSupplier(Boolean supplier) {
        this.supplier = supplier;
        refreshRowCount();
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
        refreshRowCount();
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, PeopleCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

}
