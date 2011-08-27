package com.bee32.icsf.principal.builtins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bee32.plover.ox1.principal.IPrincipal;
import com.bee32.plover.ox1.principal.IPrincipalVisitor;
import com.bee32.plover.ox1.principal.Principal;

public class MultiPrincipal
        extends Principal
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private List<IPrincipal> inheritances;

    public MultiPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.inheritances = new ArrayList<IPrincipal>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        if (equals(principal))
            return true;
        for (IPrincipal i : inheritances) {
            if (i.implies(principal))
                return true;
        }
        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            for (IPrincipal i : inheritances)
                visitor.visitImplication(i);
            visitor.endPrincipal(this);
        }
    }

    public List<IPrincipal> getInheritances() {
        return Collections.unmodifiableList(inheritances);
    }

    public void addInheritance(IPrincipal inheritance) {
        inheritances.add(inheritance);
    }

    public void removeInheritance(IPrincipal inheritance) {
        inheritances.remove(inheritance);
    }

}
