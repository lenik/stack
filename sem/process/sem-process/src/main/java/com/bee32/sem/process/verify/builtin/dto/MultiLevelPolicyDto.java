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
import com.bee32.sem.process.verify.builtin.MultiLevelPolicy;

public class MultiLevelPolicyDto
        extends VerifyPolicyDto {

    private static final long serialVersionUID = 1L;

    public static final int LEVELS = 1;

    List<MultiLevelDto> levels;

    public MultiLevelPolicyDto() {
        super();
    }

    public MultiLevelPolicyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(VerifyPolicy _source) {
        MultiLevelPolicy source = (MultiLevelPolicy) _source;
        if (selection.contains(LEVELS))
            setLevels(marshalList(MultiLevelDto.class, source.getLevels()));
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy _target) {
        MultiLevelPolicy target = (MultiLevelPolicy) _target;
        if (selection.contains(LEVELS))
            mergeList(target, "levels", levels);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        if (selection.contains(LEVELS)) {
            levels = new ArrayList<MultiLevelDto>();
            String[] levelStrs = map.getStringArray("levels");
            if (levelStrs != null)
                for (String levelStr : levelStrs) {
                    int comma = levelStr.indexOf(',');
                    String _limit = levelStr.substring(0, comma);
                    String _policyId = levelStr.substring(comma + 1);

                    long limit = Long.parseLong(_limit);
                    int policyId = Integer.parseInt(_policyId);
                    VerifyPolicyDto policyRef = new VerifyPolicyDto().ref(policyId);

                    MultiLevelDto level = new MultiLevelDto().create();
                    level.setMultiLevel(new MultiLevelPolicyDto().ref(this));
                    level.setLimit(limit);
                    level.setTargetPolicy(policyRef);

                    levels.add(level);
                }
        }
    }

    /**
     * @see MultiLevelPolicy#getLevels()
     */
    boolean resort = false;

    public List<MultiLevelDto> getLevels() {
        return levels;
    }

    public void setLevels(List<MultiLevelDto> levels) {
        if (resort && levels != null)
            Collections.sort(levels, new AbstractNonNullComparator<MultiLevelDto>() {
                @Override
                public int compareNonNull(MultiLevelDto a, MultiLevelDto b) {
                    Long al = a.getLimit();
                    Long bl = b.getLimit();
                    return al.compareTo(bl);
                }
            });
        this.levels = levels;
    }

    static {
        EntityHelper.getInstance(MultiLevelPolicy.class).setSelection(LEVELS);
    }

}
