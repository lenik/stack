package com.bee32.sem.process.state;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.IUserFriendly;
import com.bee32.plover.ox1.color.UIEntity;

/**
 * 实体状态定义
 *
 * 定义实体的一个状态。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "entity_state_def_seq", allocationSize = 1)
public class EntityStateDef
        extends EntityAuto<Integer>
        implements ITypeAbbrAware, IUserFriendly {

    private static final long serialVersionUID = 1L;

    public static final int LABEL_LENGTH = UIEntity.LABEL_LENGTH;
    public static final int DESCRIPTION_LENGTH = UIEntity.DESCRIPTION_LENGTH;

    String label = "";
    String description = "";

    Class<?> entityClass;
    int stateNum;
    List<EntityStateOp> ops = new ArrayList<EntityStateOp>();

    /**
     * 实体类缩写
     *
     * 状态所属的实体类的缩写。
     */
    @Column(name = "entity", length = ABBR_LEN, nullable = false)
    public String getEntityAbbr() {
        return ABBR.abbr(entityClass);
    }

    public void setEntityAbbr(String entityAbbr) {
        if (entityAbbr == null)
            throw new NullPointerException("entityAbbr");
        try {
            entityClass = ABBR.expand(entityAbbr);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * 实体类
     *
     * 状态所属的实体类。
     */
    @Transient
    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
    }

    /**
     * 状态号
     *
     * 要定义的状态号。
     */
    @Column(nullable = false)
    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
    }

    /**
     * 状态名称
     *
     * 定义自定义状态的名称。
     */
    @Column(length = LABEL_LENGTH, nullable = false)
    @DefaultValue("''")
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        if (label == null)
            throw new NullPointerException("label");
        this.label = label;
    }

    /**
     * 状态描述
     *
     * 描述自定义状态的用途。
     */
    @Column(length = DESCRIPTION_LENGTH, nullable = false)
    @DefaultValue("''")
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException("description");
        this.description = description;
    }

    /**
     * 操作员列表
     *
     * 在该状态下下的操作员列表。
     */
    @OneToMany(mappedBy = "stateDef", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<EntityStateOp> getOps() {
        return ops;
    }

    public void setOps(List<EntityStateOp> ops) {
        if (ops == null)
            throw new NullPointerException("managers");
        this.ops = ops;
    }

}
