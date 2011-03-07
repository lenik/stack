package com.bee32.icsf.principal;

import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.entity.IEntity;

public abstract class AbstractPrincipal
        extends Component
        implements IPrincipal, IEntity<Long> {

    private static final long serialVersionUID = 1L;

    protected Long id;

    public AbstractPrincipal() {
        super();
    }

    public AbstractPrincipal(String name) {
        super(name);
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String principalType = getClass().getSimpleName();
        return principalType + " :: " + getName();
    }

}
