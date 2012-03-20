package com.bee32.sem.make.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.QCResultParameter;

public class QCResultParameterDto
    extends UIEntityDto<QCResultParameter, Long> {

    private static final long serialVersionUID = 1L;

    QCResultDto parent;
    QCSpecParameterDto key;
    String value;

    @Override
    protected void _marshal(QCResultParameter source) {
        parent = mref(QCResultDto.class, source.getParent());
        key = mref(QCSpecParameterDto.class, source.getKey());
        value = source.getValue();

    }

    @Override
    protected void _unmarshalTo(QCResultParameter target) {
        merge(target, "parent", parent);
        merge(target, "key", key);
        target.setValue(value);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public QCResultDto getParent() {
        return parent;
    }

    public void setParent(QCResultDto parent) {
        this.parent = parent;
    }

    public QCSpecParameterDto getKey() {
        return key;
    }

    public void setKey(QCSpecParameterDto key) {
        this.key = key;
    }

    @NLength(max = QCResultParameter.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
