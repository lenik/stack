package com.bee32.sem.makebiz.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.makebiz.entity.MakeProcess;

public class MakeProcessDto
    extends MomentIntervalDto<MakeProcess> {

    private static final long serialVersionUID = 1L;

    public static final int STEPS = 1;
    public static final int SERIALS = 2;

    MakeTaskDto task;
    PartDto part;
    BigDecimal quantity;
    String batchNumber;
    Date deadline;

    List<MakeStepDto> steps;
    List<SerialNumberDto> serials;

    @Override
    protected void _marshal(MakeProcess source) {
        task = mref(MakeTaskDto.class, source.getTask());
        part = mref(PartDto.class, source.getPart());
        quantity = source.getQuantity();
        batchNumber = source.getBatchNumber();
        deadline = source.getDeadline();

        if (selection.contains(STEPS))
            steps = marshalList(MakeStepDto.class, source.getSteps());
        if (selection.contains(SERIALS))
            serials = marshalList(SerialNumberDto.class, source.getSerials());

    }

    @Override
    protected void _unmarshalTo(MakeProcess target) {
        merge(target, "task", task);
        merge(target, "part", part);
        target.setQuantity(quantity);
        target.setBatchNumber(batchNumber);
        target.setDeadline(deadline);

        if (selection.contains(STEPS))
            mergeList(target, "steps", steps);
        if (selection.contains(SERIALS))
            mergeList(target, "serials", serials);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeTaskDto getTask() {
        return task;
    }

    public void setTask(MakeTaskDto task) {
        this.task = task;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @NLength(max = MakeProcess.BATCH_NUMBER_LENGTH)
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = TextUtil.normalizeSpace(batchNumber);
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<MakeStepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<MakeStepDto> steps) {
        this.steps = steps;
    }

    public List<SerialNumberDto> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialNumberDto> serials) {
        this.serials = serials;
    }



}
