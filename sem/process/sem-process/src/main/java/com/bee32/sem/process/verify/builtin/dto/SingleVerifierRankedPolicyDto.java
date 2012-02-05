package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.AbstractNonNullComparator;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class SingleVerifierRankedPolicyDto
        extends VerifyPolicyDto {

    private static final long serialVersionUID = 1L;

    public static final int LEVELS = 1;

    List<SingleVerifierLevelDto> levels;

    public SingleVerifierRankedPolicyDto() {
        super();
    }

    public SingleVerifierRankedPolicyDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(VerifyPolicy _source) {
        SingleVerifierRankedPolicy source = (SingleVerifierRankedPolicy) _source;
        if (selection.contains(LEVELS))
            setLevels(marshalList(SingleVerifierLevelDto.class, source.getLevels()));
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy _target) {
        SingleVerifierRankedPolicy target = (SingleVerifierRankedPolicy) _target;
        if (selection.contains(LEVELS))
            mergeList(target, "levels", levels);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        if (selection.contains(LEVELS)) {
            levels = new ArrayList<SingleVerifierLevelDto>();
            String[] levelStrs = map.getStringArray("levels");
            if (levelStrs != null)
                for (String levelStr : levelStrs) {
                    int comma = levelStr.indexOf(',');
                    String _limit = levelStr.substring(0, comma);
                    String _policyId = levelStr.substring(comma + 1);

                    long limit = Long.parseLong(_limit);
                    int policyId = Integer.parseInt(_policyId);
                    VerifyPolicyDto policyRef = new VerifyPolicyDto().ref(policyId);

                    SingleVerifierLevelDto level = new SingleVerifierLevelDto().create();
                    level.setPolicy(this);
                    level.setLimit(limit);
                    level.setTargetPolicy(policyRef);

                    levels.add(level);
                }
        }
    }

    /**
     * @see SingleVerifierRankedPolicy#getLevels()
     */
    boolean resort = false;

    public List<SingleVerifierLevelDto> getLevels() {
        return levels;
    }

    public void setLevels(List<SingleVerifierLevelDto> levels) {
        if (resort && levels != null)
            Collections.sort(levels, new AbstractNonNullComparator<SingleVerifierLevelDto>() {
                @Override
                public int compareNonNull(SingleVerifierLevelDto a, SingleVerifierLevelDto b) {
                    Long al = a.getLimit();
                    Long bl = b.getLimit();
                    return al.compareTo(bl);
                }
            });
        this.levels = levels;
    }

    static {
        EntityHelper.getInstance(SingleVerifierRankedPolicy.class).setSelection(LEVELS);
    }

}
