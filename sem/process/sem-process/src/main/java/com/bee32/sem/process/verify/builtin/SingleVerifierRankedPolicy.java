package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.IllegalUsageError;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Principal;
import com.bee32.sem.process.verify.ForVerifyContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.util.Tuple;

@Entity
@DiscriminatorValue("V1X")
@ForVerifyContext(ISingleVerifierWithNumber.class)
public class SingleVerifierRankedPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    private List<SingleVerifierLevel> levels;
    private LevelMap levelMap;

X-Population

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
    public VerifyResult validate(IVerifyContext _context) {
        ISingleVerifierWithNumber context = checkedCast(ISingleVerifierWithNumber.class, _context);

        Principal user = context.getVerifier1();

        if (!user.impliesOneOf(getResponsibles(context)))
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

    @Transient
    @Override
    public Set<?> getPredefinedStages() {
        Set<Object> predefinedStages = new HashSet<Object>();
        for (Entry<Long, SingleVerifierLevel> entry : getLevelMap().entrySet()) {
            Long limit = entry.getKey();
            VerifyPolicy targetPolicy = entry.getValue().getTargetPolicy();
            for (Object targetStage : targetPolicy.getPredefinedStages()) {
                Tuple predefinedStage = new Tuple(limit, targetStage);
                predefinedStages.add(predefinedStage);
            }
        }
        return predefinedStages;
    }

    @Override
    public Tuple getStage(IVerifyContext context) {
        ISingleVerifierWithNumber svn = checkedCast(ISingleVerifierWithNumber.class, context);
        long judgeNumber = svn.getJudgeNumber().longValue();
        Entry<Long, SingleVerifierLevel> declaredEntry = getLevelMap().ceilingEntry(judgeNumber);
        long limit = declaredEntry.getKey();
        VerifyPolicy targetPolicy = declaredEntry.getValue().getTargetPolicy();
        Object targetStage = targetPolicy.getStage(context);
        return new Tuple(limit, targetStage);
    }

    @Override
    public Set<Principal> getStageResponsibles(Object stage) {
        Tuple tuple = (Tuple) stage;
        Long limit = tuple.getFactor(0);
        if (limit == null)
            throw new IllegalUsageError("Bad limit: " + limit);
        Object targetStage = tuple.getFactor(1);
        VerifyPolicy targetPolicy = getLevelMap().get(limit).getTargetPolicy();
        return targetPolicy.getStageResponsibles(targetStage);
    }

}
