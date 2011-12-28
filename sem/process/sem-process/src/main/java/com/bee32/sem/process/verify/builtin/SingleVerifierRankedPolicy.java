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
import com.bee32.sem.process.verify.ForVerifyContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

@Entity
@DiscriminatorValue("ML")
@ForVerifyContext(ISingleVerifierWithNumber.class)
public class SingleVerifierRankedPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    private List<SingleVerifierLevel> levels;
    private LevelMap levelMap;

    /**
     * @return Non-null range set.
     */
    @OneToMany(mappedBy = "policy", orphanRemoval = true)
    @OrderBy("limit_ asc")
    @Cascade({ CascadeType.ALL })
    public synchronized List<SingleVerifierLevel> getLevels() {
        if (levels == null) {
            if (levelMap == null) {
                levels = new ArrayList<SingleVerifierLevel>();
            } else {
                levels = convertToLevels(levelMap);
                levelMap = null;
            }
        }
        return levels;
    }

    public synchronized void setLevels(List<SingleVerifierLevel> levels) {
        this.levels = levels;
        this.levelMap = null;
    }

    public SingleVerifierLevel getLevel(long limit) {
        if (levelMap != null)
            return levelMap.getTarget(limit);

        if (levels != null) {
            for (SingleVerifierLevel level : levels)
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

    static LevelMap convertToRangeMap(Collection<SingleVerifierLevel> levels) {
        LevelMap map = new LevelMap();
        if (levels != null)
            for (SingleVerifierLevel range : levels) {
                long limit = range.getLimit();
                map.put(limit, range);
            }
        return map;
    }

    static List<SingleVerifierLevel> convertToLevels(LevelMap map) {
        List<SingleVerifierLevel> levels = new ArrayList<SingleVerifierLevel>();
        levels.addAll(map.values());
        return levels;
    }

    public void addLevel(long limit, VerifyPolicy verifyPolicy) {
        if (verifyPolicy == null)
            throw new NullPointerException("verifyPolicy for " + getName());

        SingleVerifierLevel range = new SingleVerifierLevel(this, limit, verifyPolicy);
        getLevelMap().put(limit, range);
    }

    public boolean removeLevel(long limit) {
        return getLevelMap().remove(limit) != null;
    }

    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifyContext _context) {
        ISingleVerifierWithNumber svn = null;

        if (_context != null)
            svn = checkedCast(ISingleVerifierWithNumber.class, _context);

        return getDeclaredResponsibles(svn);
    }

    Set<Principal> getDeclaredResponsibles(ISingleVerifierWithNumber context) {
        if (context == null)
            throw new NullPointerException("Context of " + ISingleVerifierWithNumber.class + " is required.");

        long limit = context.getJudgeNumber().longValue();

        Set<Principal> allDeclared = new HashSet<Principal>();
        if (levelMap == null)
            return null;

        Long ceil = levelMap.ceilingKey(limit);

        IdentityHashSet markSet = new IdentityHashSet();
        markSet.add(this);

        while (ceil != null) {

            SingleVerifierLevel level = levelMap.get(ceil);

            VerifyPolicy subPolicy = level.getTargetPolicy();

            // Already scanned, skip to avoid cyclic ref.
            if (!markSet.add(subPolicy))
                continue;

            if (subPolicy instanceof SingleVerifierRankedPolicy) {
                SingleVerifierRankedPolicy subML = (SingleVerifierRankedPolicy) subPolicy;
                Collection<? extends Principal> subset = subML.getDeclaredResponsibles(context);
                allDeclared.addAll(subset);

            } else if (subPolicy.getMetadata().isUsefulFor(ISingleVerifier.class)) {
                Set<Principal> dr = subPolicy.getDeclaredResponsibles(context);
                allDeclared.addAll(dr);
            }

            ceil = levelMap.higherKey(ceil);
        }

        return allDeclared;
    }

    @Override
    public VerifyResult validate(IVerifyContext _context) {
        ISingleVerifierWithNumber context = checkedCast(ISingleVerifierWithNumber.class, _context);

        User user = context.getVerifier1();

        if (!user.impliesOneOf(getDeclaredResponsibles(context)))
            return VerifyResult.invalid(user);

        return VERIFIED;
    }

    @Override
    public VerifyResult evaluate(IVerifyContext _context) {
        ISingleVerifierWithNumber context = checkedCast(ISingleVerifierWithNumber.class, _context);

        if (context.getVerifier1() == null)
            return UNKNOWN;

        if (!context.isAccepted1())
            return VerifyResult.rejected(context.getVerifier1(), context.getRejectedReason1());

        return VERIFIED;
    }

}
