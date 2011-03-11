package com.bee32.icsf.principal;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class AbstractPrincipal
        extends Entity<Long>
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    private String displayName;
    private String description;

    public AbstractPrincipal() {
        super();
    }

    public AbstractPrincipal(String name) {
        super(name);
    }

    public String getDisplayName() {
        return displayName;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        switch (format) {

        case SHORT:
            String principalType = getClass().getSimpleName();
            String qname = principalType + " :: " + getName();
            out.print(qname);
            break;

        default:
            super.toString(out, format);
        }
    }

}
