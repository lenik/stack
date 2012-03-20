package com.bee32.sem.make.dto;

import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.QCSpec;

public class QCSpecDto
    extends UIEntityDto<QCSpec, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARAMETERS = 1;

    String text;
    List<QCSpecParameterDto> parameters;

    @Override
    protected void _marshal(QCSpec source) {
        text = source.getText();
        if (selection.contains(PARAMETERS))
            parameters = marshalList(QCSpecParameterDto.class, source.getParameters());

    }

    @Override
    protected void _unmarshalTo(QCSpec target) {
        target.setText(text);
        if (selection.contains(PARAMETERS))
            mergeList(target, "parameters", parameters);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    @NLength(max = QCSpec.TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<QCSpecParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<QCSpecParameterDto> parameters) {
        this.parameters = parameters;
    }



}
