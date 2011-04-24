package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
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

    public PassToNextDto(PassToNext source, int selection) {
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
    protected void _unmarshalTo(IUnmarshalContext context, PassToNext target) {
        target.setName(name);
        target.setDescription(description);

        if (selection.contains(SEQUENCES))
            target.setSequences(unmarshalList(sequences));
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
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
