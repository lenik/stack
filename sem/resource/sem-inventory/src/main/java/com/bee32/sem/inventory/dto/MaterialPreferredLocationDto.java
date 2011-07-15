package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;

public class MaterialPreferredLocationDto
        extends EntityDto<MaterialPreferredLocation, Long> {

    private static final long serialVersionUID = 1L;

    private MaterialDto material;
    private String batch;
    private StockLocationDto location;
    private String comment;

    @Override
    protected void _marshal(MaterialPreferredLocation source) {
        this.material = DTOs.mref(MaterialDto.class, source.getMaterial());
        this.batch = source.getBatch();
        // XXX
        // this.loaction = source.getLocation();
        this.comment = source.getComment();
    }

    @Override
    protected void _unmarshalTo(MaterialPreferredLocation target) {
        merge(target, "material", material);
        target.setBatch(batch);
        // XXX location
        target.setComment(comment);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
