package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.AbstractNonNullComparator;
import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.MultiLevel;

public class MultiLevelDto
        extends EntityDto<MultiLevel, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int LEVELS = 1;

    String name;
    String description;

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

    public MultiLevelDto(MultiLevel source, int selection) {
        super(source, selection);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LevelDto> getLevels() {
        return levels;
    }

    /**
     * @see MultiLevel#getLevels()
     */
    boolean resort = false;

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

    @Override
    protected void _marshal(MultiLevel source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(LEVELS))
            setLevels(marshalListRec(LevelDto.class, source.getLevels()));
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, MultiLevel target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(LEVELS))
            target.setLevels(LevelDto.unmarshalList(context, levels));
    }

    @Override
    public void _parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {

        if (map.containsKey("id") && !map.getString("id").isEmpty())
            id = map.getInt("id");

        name = map.getString("name");
        description = map.getString("description");

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
                    level.setLimit(limit);
                    level.setTargetPolicy(policyRef);

                    levels.add(level);
                }
        }
    }

}
