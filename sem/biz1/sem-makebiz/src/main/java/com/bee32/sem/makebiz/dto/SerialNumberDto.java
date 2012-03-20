package com.bee32.sem.makebiz.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.makebiz.entity.SerialNumber;

public class SerialNumberDto
    extends UIEntityDto<SerialNumber, Long> {

    private static final long serialVersionUID = 1L;

    MakeProcessDto process;
    String number;

    @Override
    protected void _marshal(SerialNumber source) {
        process = mref(MakeProcessDto.class, source.getProcess());
        number = source.getNumber();

    }

    @Override
    protected void _unmarshalTo(SerialNumber target) {
        merge(target, "process", process);
        target.setNumber(number);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeProcessDto getProcess() {
        return process;
    }

    public void setProcess(MakeProcessDto process) {
        this.process = process;
    }

    @NLength(max = SerialNumber.NUMBER_LENGTH)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



}
