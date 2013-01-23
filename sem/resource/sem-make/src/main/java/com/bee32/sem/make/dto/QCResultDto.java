package com.bee32.sem.make.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.make.entity.QCResult;

public class QCResultDto
    extends MomentIntervalDto<QCResult> {

    private static final long serialVersionUID = 1L;

    public static final int PARAMETERS = 1;

    List<QCResultParameterDto> parameters;
    String memo;

    @Override
    protected void _marshal(QCResult source) {
        if (selection.contains(PARAMETERS))
            parameters = marshalList(QCResultParameterDto.class, source.getParameters());
        else
            parameters = Collections.emptyList();
        memo = source.getMemo();
    }

    @Override
    protected void _unmarshalTo(QCResult target) {
        if (selection.contains(PARAMETERS))
            mergeList(target, "parameters", parameters);
        target.setMemo(memo);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public List<QCResultParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<QCResultParameterDto> parameters) {
        this.parameters = parameters;
    }

    @NLength(max = QCResult.MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = TextUtil.normalizeSpace(memo);
    }



}
