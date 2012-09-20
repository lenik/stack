package com.bee32.plover.ox1.meta;

import javax.free.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 字段定义
 *
 * 自定义实体的字段定义，或对原有实体字段信息的重写。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "entity_column_seq", allocationSize = 1)
public class EntityColumn
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 20;
    public static final int TYPE_LENGTH = 10;

    EntityInfo entity;

    String name;
    String type;
    int precision;
    int scale;
    boolean indexed;

    String helpDoc;
    // String cssStyle;

    BackedCandidateMap1 candidates = new BackedCandidateMap1("");

    @Override
    public void populate(Object source) {
        if (source instanceof EntityColumn)
            _populate((EntityColumn) source);
        else
            super.populate(source);
    }

    protected void _populate(EntityColumn o) {
        super._populate(o);
        entity = o.entity;
        name = o.name;
        type = o.type;
        precision = o.precision;
        scale = o.scale;
        indexed = o.indexed;
        helpDoc = o.helpDoc;
        candidates = o.candidates; // XXX CLONE
    }

    /**
     * 实体
     *
     * 该字段所属的自定义实体。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public EntityInfo getEntity() {
        return entity;
    }

    public void setEntity(EntityInfo entity) {
        this.entity = entity;
    }

    /**
     * 名称
     *
     * 实体内唯一的字段名。
     */
    @NaturalId
    @Column(length = NAME_LENGTH, nullable = false)
    @Index(name = "name")
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    /**
     * 类型
     *
     * 字段的数据类型。
     */
    @Column(length = TYPE_LENGTH, nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    /**
     * 精度
     *
     * 数值型字段的精度，或者文本型字段的最大长度。
     */
    @Column(nullable = false)
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * 小数位数
     *
     * 数值型字段的小数位数。
     */
    @Column(nullable = false)
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * 索引标识
     *
     * 字段是否已索引。
     */
    @Column(nullable = false)
    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    @Transient
    public CandidateMap<String> getCandidates() {
        try {
            return candidates.getForm();
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void setCandidates(CandidateMap<String> candidates) {
        this.candidates.setForm(candidates);
    }

    /**
     * 候选项目
     *
     * 可供候选输入的项目集。
     */
    @Lob
    public String getCandidateDef() {
        return candidates.toString();
    }

    public void setCandidateDef(String candidateDef) {
        candidates.setDef(candidateDef);
    }

}
