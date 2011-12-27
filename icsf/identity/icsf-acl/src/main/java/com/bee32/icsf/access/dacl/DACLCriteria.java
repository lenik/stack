package com.bee32.icsf.access.dacl;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.criteria.hibernate.Conjunction;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Disjunction;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.PrincipalCriteria;

public class DACLCriteria
        extends CriteriaSpec {

    public static ICriteriaElement impliesDACE(Principal principal, Permission permission) {
        return compose( //
                PrincipalCriteria.impliedBy("principal", principal), //
                impliesPermission(permission));
    }

    public static CriteriaElement impliesPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        // XXX 3-state booleans.

        Disjunction disj = disjunction();
        disj.add(equals("admin", true));

        if (!permission.getAdmin()) {
            Conjunction conj = conjunction();

            if (permission.getReadable())
                conj.add(equals("readable", true));

            if (permission.getWritable())
                conj.add(equals("writable", true));

            if (permission.getExecutable())
                conj.add(equals("executable", true));

            if (permission.getListable())
                conj.add(equals("listable", true));

            if (permission.getCreatable())
                conj.add(equals("creatable", true));

            if (permission.getDeletable())
                conj.add(equals("deletable", true));

            disj.add(disj);
        }
        return disj;
    }

}
