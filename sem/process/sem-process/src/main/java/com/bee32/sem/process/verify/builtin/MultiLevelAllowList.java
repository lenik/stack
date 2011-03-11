package com.bee32.sem.process.verify.builtin;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;

public class MultiLevelAllowList
        extends AbstractVerifyPolicy<ILevelContext, AllowState>
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private final TreeMap<Integer, Set<IPrincipal>> levelMap;

    public MultiLevelAllowList() {
        super(AllowState.class);
        this.levelMap = new TreeMap<Integer, Set<IPrincipal>>();
    }

    public void add(int maxLevel, IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        Set<IPrincipal> principals = levelMap.get(maxLevel);
        if (principals == null) {
            principals = new HashSet<IPrincipal>();
            levelMap.put(maxLevel, principals);
        }
        principals.add(principal);
    }

    public Set<IPrincipal> getResponsibles(int level) {
        Set<IPrincipal> responsibles = new HashSet<IPrincipal>();
        Integer ceil = levelMap.ceilingKey(level);
        while (ceil != null) {
            Set<IPrincipal> onelevel = levelMap.get(ceil);
            responsibles.addAll(onelevel);
            ceil = levelMap.higherKey(ceil);
        }
        return responsibles;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String checkState(ILevelContext context, AllowState state) {
        return null;
    }

}
