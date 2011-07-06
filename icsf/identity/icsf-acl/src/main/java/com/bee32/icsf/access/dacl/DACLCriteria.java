package com.bee32.icsf.access.dacl;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.util.PrincipalCriteria;

public class DACLCriteria {

    public static Criterion impliesDACE(Principal principal, Permission permission) {
        Conjunction conj = Restrictions.conjunction();
        conj.add(PrincipalCriteria.implies(principal));
        conj.add(impliesPermission(permission));
        return conj;
    }

    public static Criterion impliesPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        Disjunction disj = Restrictions.disjunction();
        disj.add(Restrictions.eq("admin", true));

        if (!permission.isAdmin()) {
            Conjunction conj = Restrictions.conjunction();

            if (permission.isReadable())
                conj.add(Restrictions.eq("readable", true));

            if (permission.isWritable())
                conj.add(Restrictions.eq("writable", true));

            if (permission.isExecutable())
                conj.add(Restrictions.eq("executable", true));

            if (permission.isListable())
                conj.add(Restrictions.eq("listable", true));

            if (permission.isCreatable())
                conj.add(Restrictions.eq("creatable", true));

            if (permission.isDeletable())
                conj.add(Restrictions.eq("deletable", true));

            disj.add(disj);
        }
        return disj;
    }

}
