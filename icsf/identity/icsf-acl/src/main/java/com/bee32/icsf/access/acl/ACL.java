package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.principal.IPrincipal;

public class ACL
        extends ArrayACL {

    private IACLPolicy policy;

    private final IACL parent;

    private Map<IPrincipal, PrincipalACL> _pmap;

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
    public IACLPolicy getACLPolicy() {
        return policy;
    }

    @Override
    protected IACL newACLRange() {
        return new ACL(policy, parent, null);
    }

    protected Map<IPrincipal, PrincipalACL> getPMap() {
        if (_pmap == null) {
            synchronized (this) {
                if (_pmap == null) {
                    _pmap = new HashMap<IPrincipal, PrincipalACL>();

                    for (Entry entry : getEntries())
                        onEntryChanged(entry, false);
                }
            }
        }
        return _pmap;
    }

    @Override
    protected void onEntryChanged(Entry entry, boolean removed) {
        if (_pmap == null)
            return;

        IPrincipal principal = entry.getPrincipal();
        PrincipalACL pacl = _pmap.get(principal);

        if (removed) {
            if (pacl == null)
                return;
            else
                _pmap = null; // invalidate immediately.
        } else {
            if (pacl == null) {
                pacl = new PrincipalACL(this, principal);
                _pmap.put(principal, pacl);
            }
            pacl.add(entry);
        }
    }

    /**
     * XXX - imply-relation
     */
    @Override
    public PrincipalACL select(IPrincipal principal) {
        PrincipalACL principalACL = getPMap().get(principal);
        return principalACL;
    }

}
