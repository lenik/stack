package com.bee32.plover.ox1.meta;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;

public class EntityColumnDto
        extends UIEntityDto<EntityColumn, Integer> {

    private static final long serialVersionUID = 1L;

    EntityInfo entity;

    String name;
    String type;
    int precision;
    int scale;

    // HelpDoc helpdoc;
    boolean indexed;

    // String cssStyle;

    BackedCandidateMap1 candidates = new BackedCandidateMap1("");

    @Override
    protected void _marshal(EntityColumn source) {
        name = source.getName();
        type = source.getType();
        precision = source.getPrecision();
        scale = source.getScale();
        indexed = source.isIndexed();
        candidates = source.getBackedCandidates();
    }

    @Override
    protected void _unmarshalTo(EntityColumn target) {
        target.setName(name);
        target.setType(type);
        target.setPrecision(precision);
        target.setScale(scale);
        target.setIndexed(indexed);
        target.setBackedCandidates(candidates);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");
        type = map.getString("type");
        precision = map.getInt("precision");
        scale = map.getInt("scale");
        indexed = map.getBoolean("indexed");

        candidates.setDef(map.getString("candidateDef"));
    }

    public EntityInfo getEntity() {
        return entity;
    }

    public void setEntity(EntityInfo entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public String getCandidateDef() {
        return candidates.toString();
    }

    public void setCandidateDef(String candidateDef) {
        candidates.setDef(candidateDef);
    }

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

    public BackedCandidateMap1 getBackedCandidates() {
        return candidates;
    }

    public void setBackedCandidates(BackedCandidateMap1 candidates) {
        this.candidates = candidates;
    }

}
