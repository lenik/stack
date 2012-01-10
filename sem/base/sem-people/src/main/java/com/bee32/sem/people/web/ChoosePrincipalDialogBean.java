package com.bee32.sem.people.web;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChoosePrincipalDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    String pattern;

    public <E extends Entity<?>, D extends EntityDto<? super E, ?>> ChoosePrincipalDialogBean(Class<E> entityClass,
            Class<D> dtoClass, int selection, ICriteriaElement... criteriaElements) {
        super(Principal.class, PrincipalDto.class, 0);
    }

    // Just update the view!
    public void search() {
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        Like like = null;
        if (pattern != null)
            like = new Like("name", "%" + pattern + "%");
        setCriteriaElements(like);
    }

}
