package com.bee32.sem.make.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.QCSpecParameter;

public class QCSpecParameterDto
    extends UIEntityDto<QCSpecParameter, Integer> {

    private static final long serialVersionUID = 1L;

    QCSpecDto parent;
    String value;
    boolean required;

    @Override
    protected void _marshal(QCSpecParameter source) {
        parent = marshal(QCSpecDto.class, source.getParent());
        value = source.getValue();
        required = source.isRequired();

    }

    @Override
    protected void _unmarshalTo(QCSpecParameter target) {
        merge(target, "parent", parent);
        target.setValue(value);
        target.setRequired(required);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public QCSpecDto getParent() {
        return parent;
    }

    public void setParent(QCSpecDto parent) {
        this.parent = parent;
    }

    @NLength(max = QCSpecParameter.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }



}
