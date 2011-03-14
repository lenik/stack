package com.bee32.sem.process.verify.builtin;

import java.util.TreeMap;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.process.verify.VerifyPolicy;

@Entity
@DiscriminatorValue("mlist")
public class MultiLevelAllowList
        extends VerifyPolicy<ILevelContext, AllowState> {

    private static final long serialVersionUID = 1L;

    private final TreeMap<Long, AllowList> levelMap;

    public MultiLevelAllowList() {
        super(AllowState.class);
        this.levelMap = new TreeMap<Long, AllowList>();
    }

    public void add(int maxLevel, AllowList allowList) {
        if (allowList == null)
            throw new NullPointerException("allowList");

        levelMap.put(maxLevel, allowList);
    }

    public AllowList getResponsibles(int level) {
        AllowList responsibles = new HashAllowList();
        Integer ceil = levelMap.ceilingKey(level);
        while (ceil != null) {
            AllowList onelevel = levelMap.get(ceil);
            responsibles.addAll(onelevel);
            ceil = levelMap.higherKey(ceil);
        }
        return responsibles;
    }

    @Override
    public String checkState(ILevelContext context, AllowState state) {
        return null;
    }

}
