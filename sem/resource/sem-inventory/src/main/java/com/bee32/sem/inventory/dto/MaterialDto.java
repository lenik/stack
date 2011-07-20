package com.bee32.sem.inventory.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialXP;
import com.bee32.sem.world.thing.ThingDto;

public class MaterialDto
        extends ThingDto<Material, MaterialXP> {

    private static final long serialVersionUID = 1L;

    public static final int ATTRBUTES = 1;
    public static final int ATTACHMENTS = 2;
    public static final int OPTIONS = 4;

    private MaterialCategoryDto category;
    private String serial;
    private String barCode;
    private List<MaterialAttributeDto> attributes;
    private List<UserFileDto> attachments;
    private List<MaterialWarehouseOptionDto> options;
    private List<MaterialPreferredLocationDto> preferredLocations;

    @Override
    protected void _marshal(Material source) {
        category = mref(MaterialCategoryDto.class, source.getCategory());
        serial = source.getSerial();
        barCode = source.getBarCode();

        if (selection.contains(ATTRBUTES))
            attributes = marshalList(MaterialAttributeDto.class, 0, source.getAttributes());

        preferredLocations = marshalList(MaterialPreferredLocationDto.class, source.getPreferredLocations());
        options = marshalList(MaterialWarehouseOptionDto.class, source.getOptions());

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments());
    }

    @Override
    protected void _unmarshalTo(Material target) {
        merge(target, "category", category);
        target.setSerial(serial);
        target.setBarCode(barCode);
        mergeList(target, "attributes", attributes);
        mergeList(target, "attchments", attachments);
        mergeList(target, "preferredLocations", preferredLocations);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public void addAttribute(MaterialAttributeDto attr) {
        this.attributes.add(attr);
    }

    public void addOption(MaterialWarehouseOptionDto option) {
        this.options.add(option);
    }

    public void addPreferredLocation(MaterialPreferredLocationDto preferredLocation) {
        this.preferredLocations.add(preferredLocation);
    }

    public MaterialCategoryDto getCategory() {
        return category;
    }

    public void setCategory(MaterialCategoryDto category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<MaterialAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MaterialAttributeDto> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    public List<UserFileDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFileDto> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    public List<MaterialWarehouseOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<MaterialWarehouseOptionDto> options) {
        if (options == null)
            throw new NullPointerException("options");
        this.options = options;
    }

    public List<MaterialPreferredLocationDto> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(List<MaterialPreferredLocationDto> preferredLocations) {
        if (preferredLocations == null)
            throw new NullPointerException("preferredLocations");
        this.preferredLocations = preferredLocations;
    }
}
