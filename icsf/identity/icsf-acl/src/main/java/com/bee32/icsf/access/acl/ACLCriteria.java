package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.plover.criteria.hibernate.Conjunction;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Disjunction;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class ACLCriteria
        extends CriteriaSpec {

    public static ICriteriaElement impliesEntry(Principal principal, Permission permission) {
        return compose( //
                PrincipalCriteria.inImSet("principal", principal), //
                impliesPermission(permission));
    }

    public static CriteriaElement impliesPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        // XXX 3-state booleans.

        Disjunction disj = disjunction();
        disj.add(equals("admin", true));

        if (!permission.isAdmin()) {
            Conjunction conj = conjunction();

            if (permission.isUsable())
                conj.add(equals("usable", true));

            if (permission.isReadable())
                conj.add(equals("readable", true));

            if (permission.isWritable())
                conj.add(equals("writable", true));

            if (permission.isExecutable())
                conj.add(equals("executable", true));

            if (permission.isCreatable())
                conj.add(equals("creatable", true));

            if (permission.isDeletable())
                conj.add(equals("deletable", true));

            disj.add(disj);
        }
        return disj;
    }

}
