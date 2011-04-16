package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.MultiLevel;

public class MultiLevelDto
        extends EntityDto<MultiLevel, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int RANGES = 1;

    String name;
    String description;

    List<MultiLevelRangeDto> ranges;

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

    public List<MultiLevelRangeDto> getRanges() {
        return ranges;
    }

    public void setRanges(List<MultiLevelRangeDto> ranges) {
        this.ranges = ranges;
    }

    @Override
    protected void _marshal(MultiLevel source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(RANGES))
            ranges = MultiLevelRangeDto.marshalList(source.getRanges());
    }

    @Override
    protected void _unmarshalTo(MultiLevel target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(RANGES))
            target.setRanges(MultiLevelRangeDto.unmarshalSet(ranges));
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        name = map.getString("name");
        description = map.getString("description");

        if (selection.contains(RANGES)) {
            ranges = new ArrayList<MultiLevelRangeDto>();
            String[] rangeIds = map.getStringArray("ranges");
            if (rangeIds != null)
                for (String rangeId : rangeIds) {
                    MultiLevelRangeDto range = new MultiLevelRangeDto();
                    range.setId(Integer.parseInt(rangeId));
                    ranges.add(range);
                }
        }
    }

    public static List<MultiLevelDto> marshalList(int selection, Iterable<? extends MultiLevel> entities) {
        return marshalList(MultiLevelDto.class, selection, entities);
    }

}
