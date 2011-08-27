package com.bee32.sem.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.purchase.entity.MakeTaskItem;
import com.bee32.sems.bom.dto.PartDto;

public class MakeTaskItemDto
        extends MomentIntervalDto<MakeTaskItem> {

    private static final long serialVersionUID = 1L;

    MakeTaskDto task;
    PartDto part;
    BigDecimal quantity;

    Date deadline;
    String status;

    @Override
    protected void _marshal(MakeTaskItem source) {
        task = mref(MakeTaskDto.class, source.getTask());
        part = mref(PartDto.class, source.getPart());
        quantity = source.getQuantity();

        deadline = source.getDeadline();
        status = source.getStatus();
    }

    @Override
    protected void _unmarshalTo(MakeTaskItem target) {
        merge(target, "task", task);
        merge(target, "part", part);
        target.setQuantity(quantity);
        target.setDeadline(deadline);
        target.setStatus(status);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public MakeTaskDto getTask() {
        return task;
    }

    public void setTask(MakeTaskDto task) {
        if (task == null)
            throw new NullPointerException("task");
        this.task = task;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        if (part == null)
            throw new NullPointerException("part");
        this.part = part;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(task), //
                naturalId(part));
    }

}
