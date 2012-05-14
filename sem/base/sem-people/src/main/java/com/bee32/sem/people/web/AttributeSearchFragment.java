package com.bee32.sem.people.web;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.people.util.PeopleCriteria;

public class AttributeSearchFragment extends SearchFragment {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String pattern;

    AttributeSearchFragment(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getEntryLabel() {
        return pattern;
    }

    @Override
    public ICriteriaElement compose() {
        if (pattern.equals("是内部员工"))
            return PeopleCriteria.isEmployee();
        if (pattern.equals("是客户"))
            return PeopleCriteria.isCustomer();
        if (pattern.equals("是供应商"))
            return PeopleCriteria.isSupplier();
        return null;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttributeSearchFragment other = (AttributeSearchFragment) obj;
        if (pattern == null) {
            if (other.pattern != null)
                return false;
        } else if (!pattern.equals(other.pattern))
            return false;
        return true;
    }

}
