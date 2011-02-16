package com.bee32.sems.security.access.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bee32.sems.security.access.IPrincipal;

public class ACL
        extends ArrayACL {

    // @Inject
    private IACLPolicy policy;

    private final IACL parent;

    private boolean parsed;
    private List<IPrincipal> declaredPrincipals;
    private Map<IPrincipal, PrincipalACL> pmap;

    public ACL() {
        this(null);
    }

    public ACL(IACL parent) {
        this(parent, null);
    }

    public ACL(IACL parent, Collection<? extends Entry> entries) {
        this(DenyPriorACLPolicy.getInstance(), parent, entries);
    }

    public ACL(IACLPolicy policy, IACL parent, Collection<? extends Entry> entries) {
        super(entries);
        if (policy == null)
            throw new NullPointerException("policy");
        this.policy = policy;
        this.parent = parent;
    }

    @Override
    public IACL getInheritedACL() {
        return parent;
    }

    @Override
    public Collection<? extends IPrincipal> getDeclaredPrincipals() {
        if (declaredPrincipals == null) {
            declaredPrincipals = new ArrayList<IPrincipal>();
            for (Entry entry : getEntries()) {
                IPrincipal p = entry.getPrincipal();
                declaredPrincipals.add(p);
            }
        }
        return declaredPrincipals;
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return policy;
    }

    @Override
    protected IACL newACLRange() {
        return new ACL(policy, parent, null);
    }

    /**
     * XXX - imply-relation
     */
    @Override
    public PrincipalACL select(IPrincipal principal) {
        classifyEntries();
        PrincipalACL principalACL = pmap.get(principal);
        return principalACL;
    }

    private synchronized void classifyEntries() {
        if (!parsed) {
            for (Entry entry : getEntries()) {
                IPrincipal principal = entry.getPrincipal();
                PrincipalACL pacl;
                if (declaredPrincipals.add(principal)) {
                    pacl = new PrincipalACL(this, principal);
                    pmap.put(principal, pacl);
                } else {
                    pacl = pmap.get(principal);
                    assert pacl != null;
                }
                pacl.add(entry);
            }
            parsed = true;
        }
    }

}
