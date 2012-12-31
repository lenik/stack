package com.bee32.sem.makebiz.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.makebiz.entity.MakeStep;

public class MakeStepDto
    extends MomentIntervalDto<MakeStep>
    implements IEnclosedObject<MakeProcessDto> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int ITEM_OPERATORS = 64 | ITEMS;

    MakeProcessDto parent;

    // Behavior as <model.part, model.processOrder>.
    MakeStepModelDto model;

    boolean done;

    List<MakeStepItemDto> items;

    @Override
    protected void _marshal(MakeStep source) {
        parent = mref(MakeProcessDto.class, source.getParent());
        model = mref(MakeStepModelDto.class, source.getModel());
        if (selection.contains(ITEMS)) {
            items = marshalList(MakeStepItemDto.class, //
                    selection.translate(ITEM_OPERATORS, MakeStepItemDto.OPERATORS), //
                    source.getItems());
        } else
            items = new ArrayList<MakeStepItemDto>();
        done = source.isDone();

    }

    @Override
    protected void _unmarshalTo(MakeStep target) {
        merge(target, "parent", parent);
        merge(target, "model", model);
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
        target.setDone(done);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeProcessDto getParent() {
        return parent;
    }

    public void setParent(MakeProcessDto parent) {
        this.parent = parent;
    }

    public MakeStepModelDto getModel() {
        return model;
    }

    public void setModel(MakeStepModelDto model) {
        this.model = model;
    }

    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent),
                naturalId(model));
    }

    @Override
    public MakeProcessDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(MakeProcessDto enclosingObject) {
        setParent(enclosingObject);

    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<MakeStepItemDto> getItems() {
        return items;
    }

    public void setItems(List<MakeStepItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        for (MakeStepItemDto item : items)
            item.setParent(this);
        this.items = items;
    }

    public synchronized void addItem(MakeStepItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        item.setParent(this);
        items.add(item);
    }

    public synchronized void removeItem(MakeStepItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        // item.detach();

    }

}
