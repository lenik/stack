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
     * Must override Entity-Info before add EntityColumn.
     */
    @NaturalId
    @ManyToOne(optional = false)
    public EntityInfo getEntity() {
        return entity;
    }

    public void setEntity(EntityInfo entity) {
        this.entity = entity;
    }

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

    @Column(length = TYPE_LENGTH, nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    @Column(nullable = false)
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Column(nullable = false)
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

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

    @Lob
    String getCandidateDef() {
        return candidates.toString();
    }

    void setCandidateDef(String candidateDef) {
        candidates.setDef(candidateDef);
    }

}
