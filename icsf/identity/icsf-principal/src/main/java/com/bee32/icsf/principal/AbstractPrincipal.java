package com.bee32.icsf.principal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

//@Entity
@MappedSuperclass
// @DiscriminatorColumn(name = "steoro", length = 20)
public abstract class AbstractPrincipal
        extends EntityBean<Long>
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

    @Basic(optional = false)
    @Column(length = 60)
    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Column(length = 50)
    @Override
    public String getDisplayName() {
        return displayName;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(length = 200)
    @Override
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
