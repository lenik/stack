package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.Alias;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.result.ErrorResult;
import com.bee32.sem.process.verify.result.RejectedResult;
import com.bee32.sem.process.verify.result.UnauthorizedResult;

@Entity
@DiscriminatorValue("ML")
@Alias("level")
public class MultiLevel
        extends VerifyPolicy<IValueHolder> {

    private static final long serialVersionUID = 1L;

    private List<Level> levels;
    private LevelMap levelMap;

    /**
     * @return Non-null range set.
     */
    @OneToMany(mappedBy = "multiLevel")
    @OrderBy("limit_ asc")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public synchronized List<Level> getLevels() {
        if (levels == null) {
            if (levelMap == null) {
                levels = new ArrayList<Level>();
            } else {
                levels = convertToLevels(levelMap);
                levelMap = null;
            }
        }
        return levels;
    }

    public synchronized void setLevels(List<Level> levels) {
        this.levels = levels;
        this.levelMap = null;
    }

    public Level getLevel(long limit) {
        if (levelMap != null)
            return levelMap.get(limit);

        if (levels != null) {
            for (Level level : levels)
                if (level.getLimit() == limit)
                    return level;
        }

        return null;
    }

    /**
     * @return Non-<code>null</code> LevelMap.
     */
    @Transient
    public synchronized LevelMap getLevelMap() {
        if (levelMap == null)
            if (levels == null) {
                levelMap = new LevelMap();
            } else {
                levelMap = convertToRangeMap(levels);
                levels = null;
            }
        return levelMap;
    }

    public synchronized void setLevelMap(LevelMap levelMap) {
        this.levelMap = levelMap;
        this.levels = null;
    }

    static LevelMap convertToRangeMap(Collection<Level> levels) {
        LevelMap map = new LevelMap();
        if (levels != null)
            for (Level range : levels) {
                long limit = range.getLimit();
                map.put(limit, range);
            }
        return map;
    }

    static List<Level> convertToLevels(LevelMap map) {
        List<Level> levels = new ArrayList<Level>();
        levels.addAll(map.values());
        return levels;
    }

    public void addLevel(long limit, VerifyPolicy<?> verifyPolicy) {
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy for " + getName());

        Level range = new Level(this, limit, verifyPolicy);
        getLevelMap().put(limit, range);
    }

    public boolean removeLevel(long limit) {
        return getLevelMap().remove(limit) != null;
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IValueHolder valueHolder) {
        long limit = valueHolder.getLongValue();
        return getResponsiblesWithinLimit(limit);
    }

    public Collection<? extends Principal> getResponsiblesWithinLimit(long limit) {
        DummyValue compatibleContext = new DummyValue("dummy", limit);

        Set<Principal> allDeclared = new HashSet<Principal>();
        if (levelMap == null)
            return null;

        Long ceil = levelMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            Level level = levelMap.get(ceil);

            VerifyPolicy<IValueHolder> policy = (VerifyPolicy<IValueHolder>) level.getTargetPolicy();

            // Already scanned, skip to avoid cyclic ref.
            if (!markSet.add(policy))
                continue;

            if (policy instanceof MultiLevel) {
                MultiLevel mlist = (MultiLevel) policy;
                Collection<? extends Principal> subset = mlist.getResponsiblesWithinLimit(limit);
                allDeclared.addAll(subset);

            } else if (compatibleContext.isCompatibleWith(policy)) {
                Collection<? extends Principal> subset = compatibleContext.getDeclaredResponsibles(policy);
                allDeclared.addAll(subset);
            }

            ceil = levelMap.higherKey(ceil);
        }

        return allDeclared;
    }

    @Override
    public ErrorResult validate(IValueHolder context) {
        User user = context.getUser();

        if (!user.impliesOneOf(getDeclaredResponsibles(valueHolder)))
            return new UnauthorizedResult(user);

        return null;
    }

    @Override
    public ErrorResult check(IValueHolder context) {
        if (!context.isAllowed())
            return new RejectedResult(context.getUser(), context.getMessage());

        return null;
    }

}
