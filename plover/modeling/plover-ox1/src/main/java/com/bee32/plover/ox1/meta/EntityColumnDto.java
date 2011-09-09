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

    CandidateMap<String> candidates = new CandidateMap<String>();

    @Override
    protected void _marshal(EntityColumn source) {
        name = source.getName();
        type = source.getType();
        precision = source.getPrecision();
        scale = source.getScale();
        indexed = source.isIndexed();
        candidates = source.getCandidates();
    }

    @Override
    protected void _unmarshalTo(EntityColumn target) {
        target.setName(name);
        target.setType(type);
        target.setPrecision(precision);
        target.setScale(scale);
        target.setIndexed(indexed);
        target.setCandidates(candidates);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");
        type = map.getString("type");
        precision = map.getInt("precision");
        scale = map.getInt("scale");
        indexed = map.getBoolean("indexed");

        // candidates.setDef(map.getString("candidateDef"));
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

    public CandidateMap<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(CandidateMap<String> candidates) {
        if (candidates == null)
            throw new NullPointerException("candidates");
        this.candidates = candidates;
    }

}
