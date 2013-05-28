package com.bee32.sem.process.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.IUserFriendly;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.process.state.util.EntityStateInfoMap;
import com.bee32.sem.process.state.util.StateIntInfo;

public class EntityStateDefDto
        extends EntityDto<EntityStateDef, Integer>
        implements IUserFriendly, ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public static final int OPS = 1;

    String label;
    String description;

    Class<?> entityClass;
    int stateNum;
    List<EntityStateOpDto> ops = new ArrayList<EntityStateOpDto>();

    @Override
    protected void _marshal(EntityStateDef source) {
        label = source.getLabel();
        description = source.getDescription();

        entityClass = source.getEntityClass();
        stateNum = source.getStateNum();

        if (selection.contains(OPS))
            ops = marshalList(EntityStateOpDto.class, source.getOps());
        else
            ops = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(EntityStateDef target) {
        target.setLabel(label);
        target.setDescription(description);

        target.setEntityClass(entityClass);
        target.setStateNum(stateNum);

        if (selection.contains(OPS))
            mergeList(target, "ops", ops);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @NLength(max = EntityStateDef.LABEL_LENGTH)
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

    @NLength(max = EntityStateDef.DESCRIPTION_LENGTH)
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
        loadState();
    }

    public String getEntityName() {
        if (entityClass == null)
            return "（请选择数据对象）";
        String typeName = ClassUtil.getTypeName(entityClass);
        return typeName;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
        loadState();
    }

    void loadState() {
        if (entityClass == null) {
            label = "";
            description = "";
        } else {
            EntityStateInfoMap infoMap = EntityStateInfoMap.getInstance(entityClass);
            StateIntInfo stateInfo = infoMap.getInfo(stateNum);
            if (stateInfo == null) {
                label = "";
                description = "";
            } else {
                label = stateInfo.getLabel();
                description = stateInfo.getDescription();
            }
        }
    }

    public List<EntityStateOpDto> getOps() {
        return ops;
    }

    public void setOps(List<EntityStateOpDto> ops) {
        if (ops == null)
            throw new NullPointerException("ops");
        this.ops = ops;
    }

}
