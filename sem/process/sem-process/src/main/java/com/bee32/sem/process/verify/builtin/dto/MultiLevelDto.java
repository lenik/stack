package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.AbstractNonNullComparator;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.sem.process.verify.builtin.MultiLevel;

public class MultiLevelDto
        extends AbstractVerifyPolicyDto<MultiLevel> {

    private static final long serialVersionUID = 1L;

    public static final int LEVELS = 1;

    List<LevelDto> levels;

    public MultiLevelDto() {
        super();
    }

    public MultiLevelDto(MultiLevel source) {
        super(source);
    }

    public MultiLevelDto(int selection) {
        super(selection);
    }

    public MultiLevelDto(int selection, MultiLevel source) {
        super(selection, source);
    }

    @Override
    protected void _marshal(MultiLevel source) {
        if (selection.contains(LEVELS))
            setLevels(marshalList(LevelDto.class, source.getLevels()));
    }

    @Override
    protected void _unmarshalTo(MultiLevel target) {
        if (selection.contains(LEVELS))
            mergeList(target, "levels", levels);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        if (selection.contains(LEVELS)) {
            levels = new ArrayList<LevelDto>();
            String[] levelStrs = map.getStringArray("levels");
            if (levelStrs != null)
                for (String levelStr : levelStrs) {
                    int comma = levelStr.indexOf(',');
                    String _limit = levelStr.substring(0, comma);
                    String _policyId = levelStr.substring(comma + 1);

                    long limit = Long.parseLong(_limit);
                    int policyId = Integer.parseInt(_policyId);
                    VerifyPolicyDto policyRef = new VerifyPolicyDto().ref(policyId);

                    LevelDto level = new LevelDto();
                    level.setMultiLevel(new MultiLevelDto().ref(this));
                    level.setLimit(limit);
                    level.setTargetPolicy(policyRef);

                    levels.add(level);
                }
        }
    }

    /**
     * @see MultiLevel#getLevels()
     */
    boolean resort = false;

    public List<LevelDto> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelDto> levels) {
        if (resort && levels != null)
            Collections.sort(levels, new AbstractNonNullComparator<LevelDto>() {
                @Override
                public int compareNonNull(LevelDto a, LevelDto b) {
                    Long al = a.getLimit();
                    Long bl = b.getLimit();
                    return al.compareTo(bl);
                }
            });
        this.levels = levels;
    }

    static {
        EntityHelper.getInstance(MultiLevel.class).setSelection(LEVELS);
    }

}
