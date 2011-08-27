package com.bee32.plover.ox1.meta;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;

public class EntityColumnDto
        extends CEntityDto<EntityColumn, Integer> {

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

    @Override
    protected void _marshal(EntityColumn source) {
        name = source.getName();
        alias = source.getAlias();
        type = source.getType();
        precision = source.getPrecision();
        scale = source.getScale();

        description = source.getDescription();
        indexed = source.isIndexed();

        candidates = source.getBackedCandidates();
    }

    @Override
    protected void _unmarshalTo(EntityColumn target) {
        target.setName(name);
        target.setAlias(alias);
        target.setType(type);
        target.setPrecision(precision);
        target.setScale(scale);

        target.setDescription(description);
        target.setIndexed(indexed);

        target.setBackedCandidates(candidates);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");
        alias = map.getString("alias");
        type = map.getString("type");
        precision = map.getInt("precision");
        scale = map.getInt("scale");

        description = map.getString("description");
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
