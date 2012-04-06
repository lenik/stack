package com.bee32.sem.makebiz.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.makebiz.entity.MakeTaskItem;

public class MakeTaskItemDto
        extends MomentIntervalDto<MakeTaskItem>
        implements IEnclosedObject<MakeTaskDto> {

    private static final long serialVersionUID = 1L;

    public static final int PART_ATTRIBUTES = 1;

    MakeTaskDto task;
    int index;
    PartDto part;
    BigDecimal quantity;

    Date deadline;
    String status;

    MakeProcessDto process;

    @Override
    protected void _marshal(MakeTaskItem source) {
        task = mref(MakeTaskDto.class, source.getTask());
        index = source.getIndex();

        int partSelection = 0;
        if(selection.contains(PART_ATTRIBUTES)) partSelection |= PartDto.TARGET_ATTRIBUTES;
        part = mref(PartDto.class, partSelection, source.getPart());

        quantity = source.getQuantity();

        deadline = source.getDeadline();
        status = source.getStatus();

        process = marshal(MakeProcessDto.class, source.getProcess());
    }

    @Override
    protected void _unmarshalTo(MakeTaskItem target) {
        merge(target, "task", task);
        target.setIndex(index);
        merge(target, "part", part);
        target.setQuantity(quantity);
        target.setDeadline(deadline);
        target.setStatus(status);
        merge(target, "process", process);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public MakeTaskDto getEnclosingObject() {
        return getTask();
    }

    @Override
    public void setEnclosingObject(MakeTaskDto enclosingObject) {
        setTask(enclosingObject);
    }

    public MakeTaskDto getTask() {
        return task;
    }

    public void setTask(MakeTaskDto task) {
        if (task == null)
            throw new NullPointerException("task");
        this.task = task;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    // @Future
    @NotNull
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @NLength(max = MakeTaskItem.STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = TextUtil.normalizeSpace(status);
    }

    public MakeProcessDto getProcess() {
	return process;
    }

	public void setProcess(MakeProcessDto process) {
	this.process = process;
    }

	@Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(task), //
                naturalId(part));
    }

}
