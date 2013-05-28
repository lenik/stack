package com.bee32.sem.process.state.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.process.state.EntityStateDef;
import com.bee32.sem.process.state.EntityStateOp;

public class EntityStateService
        extends DataService {

    public Set<Integer> getStateImSet(Class<?> entityClass, int stateInt) {
        String abbr = ITypeAbbrAware.ABBR.abbr(entityClass);
        EntityStateDef stateDef = DATA(EntityStateDef.class).getFirst( //
                new Equals("entityAbbr", abbr), //
                new Equals("stateNum", stateInt));

        if (stateDef == null)
            return Collections.emptySet();

        Set<Integer> imIdSet = new HashSet<Integer>();
        for (EntityStateOp op : stateDef.getOps()) {
            Set<Principal> opImSet = op.getPrincipal().getImSet();
            for (Principal opIm : opImSet)
                imIdSet.add(opIm.getId());
        }

        return imIdSet;
    }

}
