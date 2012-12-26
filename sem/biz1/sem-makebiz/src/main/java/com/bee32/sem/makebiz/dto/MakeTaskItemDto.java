package com.bee32.sem.makebiz.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.material.dto.MaterialDto;

public class MakeTaskItemDto
        extends MomentIntervalDto<MakeTaskItem>
        implements IEnclosedObject<MakeTaskDto> {

    private static final long serialVersionUID = 1L;

    public static final int PART_ATTRIBUTES = 1;
    public static final int PROCESSES = 2;

    MakeTaskDto task;
    int index;
    MaterialDto material;
    BigDecimal quantity;

    Date deadline;
    String status;

    List<MakeProcessDto> processes;

    @Override
    protected void _marshal(MakeTaskItem source) {
        task = mref(MakeTaskDto.class, source.getTask());
        index = source.getIndex();

        material = mref(MaterialDto.class, MaterialDto.ATTRBUTES, source.getMaterial());

        quantity = source.getQuantity();

        deadline = source.getDeadline();
        status = source.getStatus();

        if (selection.contains(PROCESSES))
            processes = marshalList(MakeProcessDto.class, source.getProcesses());
    }

    @Override
    protected void _unmarshalTo(MakeTaskItem target) {
        merge(target, "task", task);
        target.setIndex(index);
        merge(target, "material", material);
        target.setQuantity(quantity);
        target.setDeadline(deadline);
        target.setStatus(status);
        if(selection.contains(PROCESSES))
            mergeList(target, "processes", processes);
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

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

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


	public List<MakeProcessDto> getProcesses() {
        return processes;
    }

    public void setProcesses(List<MakeProcessDto> processes) {
        this.processes = processes;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(task), //
                naturalId(material));
    }

}
