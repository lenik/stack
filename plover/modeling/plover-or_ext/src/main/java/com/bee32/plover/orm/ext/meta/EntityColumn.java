package com.bee32.plover.orm.ext.meta;

import javax.free.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "entity_column_seq", allocationSize = 1)
public class EntityColumn
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    EntityInfo entity;

    String name;
    String alias;
    String type;
    int precision;
    int scale;

    String description;
    // HelpDoc helpdoc;
    boolean indexed;

    // String cssStyle;

    BackedCandidateMap1 candidates = new BackedCandidateMap1("");

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
    @Column(length = 20, nullable = false)
    @Index(name = "name")
    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 50)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    @Column(length = 30000)
    public String getCandidateDef() {
        return candidates.toString();
    }

    public void setCandidateDef(String candidateDef) {
        candidates.setDef(candidateDef);
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

    @Transient
    public BackedCandidateMap1 getBackedCandidates() {
        return candidates;
    }

    public void setBackedCandidates(BackedCandidateMap1 _candidates) {
        this.candidates = _candidates;
    }

}
