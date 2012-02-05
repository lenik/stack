package com.bee32.sem.process.verify.builtin.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class PassToNextPolicyDto
        extends VerifyPolicyDto {

    private static final long serialVersionUID = 1L;

    public static final int SEQUENCES = 1;

    List<PassStepDto> sequences;

    public PassToNextPolicyDto() {
        super();
    }

    public PassToNextPolicyDto(int fmask) {
        super(fmask);
    }

    public List<PassStepDto> getSequences() {
        return sequences;
    }

    public void setSequences(List<PassStepDto> sequences) {
        this.sequences = sequences;
    }

    @Override
    protected void _marshal(VerifyPolicy _source) {
        PassToNextPolicy source = (PassToNextPolicy) _source;
        if (selection.contains(SEQUENCES))
            sequences = marshalList(PassStepDto.class, source.getSequences());
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy _target) {
        PassToNextPolicy target = (PassToNextPolicy) _target;
        if (selection.contains(SEQUENCES))
            mergeList(target, "sequences", sequences);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        if (selection.contains(SEQUENCES)) {
            sequences = new ArrayList<PassStepDto>();
            String[] sequenceIds = map.getStringArray("sequences");
            if (sequenceIds != null)
                for (String rangeId : sequenceIds) {
                    PassStepDto range = new PassStepDto().create();
                    range.setId(Integer.parseInt(rangeId));
                    sequences.add(range);
                }
        }
    }

    static {
        EntityHelper.getInstance(PassToNextPolicy.class).setSelection(SEQUENCES);
    }

}
