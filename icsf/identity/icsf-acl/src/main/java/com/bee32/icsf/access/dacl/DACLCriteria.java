package com.bee32.icsf.access.dacl;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.criteria.hibernate.Conjunction;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Disjunction;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.PrincipalCriteria;

public class DACLCriteria
        extends CriteriaSpec {

    public static CriteriaElement impliesDACE(Principal principal, Permission permission) {
        Conjunction conj = conjunction();
        conj.add(PrincipalCriteria.implies(principal));
        conj.add(impliesPermission(permission));
        return conj;
    }

    public static CriteriaElement impliesPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        Disjunction disj = disjunction();
        disj.add(equals("admin", true));

        if (!permission.isAdmin()) {
            Conjunction conj = conjunction();

            if (permission.isReadable())
                conj.add(equals("readable", true));

            if (permission.isWritable())
                conj.add(equals("writable", true));

            if (permission.isExecutable())
                conj.add(equals("executable", true));

            if (permission.isListable())
                conj.add(equals("listable", true));

            if (permission.isCreatable())
                conj.add(equals("creatable", true));

            if (permission.isDeletable())
                conj.add(equals("deletable", true));

            disj.add(disj);
        }
        return disj;
    }

}
