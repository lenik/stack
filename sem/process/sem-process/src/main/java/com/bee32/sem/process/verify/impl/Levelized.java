package com.bee32.sem.process.verify.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;
import com.bee32.sem.process.verify.BadVerifyDataException;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyException;

public class Levelized
        extends AbstractVerifyPolicy {

    private static final long serialVersionUID = 1L;

    private final TreeMap<Integer, Collection<IPrincipal>> levelMap;

    public Levelized() {
        this.levelMap = new TreeMap<Integer, Collection<IPrincipal>>();
    }

    public void add(int maxLevel, IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        Collection<IPrincipal> principals = levelMap.get(maxLevel);
        if (principals == null) {
            principals = new HashSet<IPrincipal>();
            levelMap.put(maxLevel, principals);
        }
        principals.add(principal);
    }

    public Collection<IPrincipal> getResponsibles(int level) {
        Set<IPrincipal> responsibles = new HashSet<IPrincipal>();
        Integer ceil = levelMap.ceilingKey(level);
        while (ceil != null) {
            Collection<IPrincipal> onelevel = levelMap.get(ceil);
            responsibles.addAll(onelevel);
            ceil = levelMap.higherKey(ceil);
        }
        return responsibles;
    }

    @Override
    public void verify(IVerifiable verifiableObject)
            throws VerifyException, BadVerifyDataException {

    }

}
