package com.bee32.sem.process.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.color.IUserFriendly;
import com.bee32.plover.util.TextUtil;

public class EntityStateDto
        extends EntityDto<EntityState, Integer>
        implements IUserFriendly {

    private static final long serialVersionUID = 1L;

    public static final int MANAGERS = 1;

    String label;
    String description;

    Class<?> entityClass;
    int state;
    List<EntityStateManagerDto> managers = new ArrayList<EntityStateManagerDto>();

    @Override
    protected void _marshal(EntityState source) {
        label = source.getLabel();
        description = source.getDescription();

        entityClass = source.getEntityClass();
        state = source.getState();

        if (selection.contains(MANAGERS))
            managers = marshalList(EntityStateManagerDto.class, source.getManagers());
        else
            managers = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(EntityState target) {
        target.setLabel(label);
        target.setDescription(description);

        target.setEntityClass(entityClass);
        target.setState(state);

        if (selection.contains(MANAGERS))
            mergeList(target, "managers", managers);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @NLength(max = EntityState.LABEL_LENGTH)
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        if (label == null)
            throw new NullPointerException("label");
        label = TextUtil.normalizeSpace(label);
        this.label = label;
    }

    @NLength(max = EntityState.DESCRIPTION_LENGTH)
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException("description");
        description = TextUtil.normalizeSpace(description);
        this.description = description;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<EntityStateManagerDto> getManagers() {
        return managers;
    }

    public void setManagers(List<EntityStateManagerDto> managers) {
        if (managers == null)
            throw new NullPointerException("managers");
        this.managers = managers;
    }

}
