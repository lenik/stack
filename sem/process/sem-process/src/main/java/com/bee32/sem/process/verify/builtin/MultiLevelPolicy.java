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

import com.bee32.plover.orm.util.Alias;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.process.verify.ISingleVerifier;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

@Entity
@DiscriminatorValue("ML")
@Alias("level")
public class MultiLevelPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    private List<MultiLevel> levels;
    private LevelMap levelMap;

    public MultiLevelPolicy() {
        super(IMultiLevelContext.class);
    }

    /**
     * @return Non-null range set.
     */
    @OneToMany(mappedBy = "multiLevel", orphanRemoval = true)
    @OrderBy("limit_ asc")
    @Cascade({ CascadeType.ALL })
    public synchronized List<MultiLevel> getLevels() {
        if (levels == null) {
            if (levelMap == null) {
                levels = new ArrayList<MultiLevel>();
            } else {
                levels = convertToLevels(levelMap);
                levelMap = null;
            }
        }
        return levels;
    }

    public synchronized void setLevels(List<MultiLevel> levels) {
        this.levels = levels;
        this.levelMap = null;
    }

    public MultiLevel getLevel(long limit) {
        if (levelMap != null)
            return levelMap.get(limit);

        if (levels != null) {
            for (MultiLevel level : levels)
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

    static LevelMap convertToRangeMap(Collection<MultiLevel> levels) {
        LevelMap map = new LevelMap();
        if (levels != null)
            for (MultiLevel range : levels) {
                long limit = range.getLimit();
                map.put(limit, range);
            }
        return map;
    }

    static List<MultiLevel> convertToLevels(LevelMap map) {
        List<MultiLevel> levels = new ArrayList<MultiLevel>();
        levels.addAll(map.values());
        return levels;
    }

    public void addLevel(long limit, VerifyPolicy verifyPolicy) {
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy for " + getName());

        MultiLevel range = new MultiLevel(this, limit, verifyPolicy);
        getLevelMap().put(limit, range);
    }

    public boolean removeLevel(long limit) {
        return getLevelMap().remove(limit) != null;
    }

    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifyContext _context) {
        long longValue = 0;

        if (_context != null) {
            IMultiLevelContext context = requireContext(IMultiLevelContext.class, _context);
            longValue = context.getLongValue();
        }

        return getResponsiblesWithinLimit(longValue);
    }

    public Set<Principal> getResponsiblesWithinLimit(long limit) {
        MultiLevelContext hintContext = new MultiLevelContext();
        hintContext.setValueDescription("hint");
        hintContext.setLongValue(limit);

        Set<Principal> allDeclared = new HashSet<Principal>();
        if (levelMap == null)
            return null;

        Long ceil = levelMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            MultiLevel level = levelMap.get(ceil);

            VerifyPolicy subPolicy = level.getTargetPolicy();

            // Already scanned, skip to avoid cyclic ref.
            if (!markSet.add(subPolicy))
                continue;

            if (subPolicy instanceof MultiLevelPolicy) {
                MultiLevelPolicy subML = (MultiLevelPolicy) subPolicy;
                Collection<? extends Principal> subset = subML.getResponsiblesWithinLimit(limit);
                allDeclared.addAll(subset);

            } else if (subPolicy.isUsefulFor(ISingleVerifier.class)) {
                allDeclared.addAll(subPolicy.getDeclaredResponsibles(hintContext));
            }

            ceil = levelMap.higherKey(ceil);
        }

        return allDeclared;
    }

    @Override
    public VerifyResult validate(IVerifyContext _context) {
        IMultiLevelContext context = requireContext(IMultiLevelContext.class, _context);

        User user = context.getVerifier();

        if (!user.impliesOneOf(getDeclaredResponsibles(context)))
            return VerifyResult.invalid(user);

        return VERIFIED;
    }

    @Override
    public VerifyResult evaluate(IVerifyContext _context) {
        IMultiLevelContext context = requireContext(IMultiLevelContext.class, _context);

        if (context.getVerifier() == null)
            return UNKNOWN;

        if (!context.isAccepted())
            return VerifyResult.rejected(context.getVerifier(), context.getRejectedReason());

        return VERIFIED;
    }

}
