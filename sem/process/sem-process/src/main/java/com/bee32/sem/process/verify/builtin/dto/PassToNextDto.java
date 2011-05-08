package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.PassToNext;

public class PassToNextDto
        extends EntityDto<PassToNext, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int SEQUENCES = 1;

    String name;
    String description;

    List<PassStepDto> sequences;

    public PassToNextDto() {
        super();
    }

    public PassToNextDto(PassToNext source) {
        super(source);
    }

    public PassToNextDto(int selection) {
        super(selection);
    }

    public PassToNextDto(int selection, PassToNext source) {
        super(selection, source);
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

    public List<PassStepDto> getSequences() {
        return sequences;
    }

    public void setSequences(List<PassStepDto> sequences) {
        this.sequences = sequences;
    }

    @Override
    protected void _marshal(PassToNext source) {
        name = source.getName();
        description = source.getDescription();

        if (selection.contains(SEQUENCES))
            sequences = marshalList(PassStepDto.class, source.getSequences());
    }

    @Override
    protected void _unmarshalTo(PassToNext target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(SEQUENCES))
            mergeList(target, "sequences", sequences);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        name = map.getString("name");
        description = map.getString("description");

        if (selection.contains(SEQUENCES)) {
            sequences = new ArrayList<PassStepDto>();
            String[] sequenceIds = map.getStringArray("sequences");
            if (sequenceIds != null)
                for (String rangeId : sequenceIds) {
                    PassStepDto range = new PassStepDto();
                    range.setId(Integer.parseInt(rangeId));
                    sequences.add(range);
                }
        }
    }

}
