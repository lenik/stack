package com.bee32.sem.sandbox;

import java.util.List;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public abstract class AbstractCriteriaHolder
        extends CriteriaSpec
        implements ICriteriaHolder {

    private static final long serialVersionUID = 1L;

    @Override
    public ICriteriaElement getCriteriaElement() {
        List<? extends ICriteriaElement> elements = getCriteriaElements();
        if (elements == null || elements.isEmpty())
            return null;
        if (elements.size() == 1)
            return elements.get(0);
        CriteriaComposite composite = new CriteriaComposite(elements);
        return composite;
    }

    protected abstract List<? extends ICriteriaElement> getCriteriaElements();

}
