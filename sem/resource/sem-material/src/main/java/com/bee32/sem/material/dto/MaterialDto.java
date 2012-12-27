package com.bee32.sem.material.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialXP;
import com.bee32.sem.world.thing.ThingDto;

public class MaterialDto
        extends ThingDto<Material, MaterialXP> {

    private static final long serialVersionUID = 1L;

    public static final int ATTRBUTES = 1;
    public static final int ATTACHMENTS = 2;
    public static final int OPTIONS = 4;
    public static final int PRICES = 8;

    public static final int PREFERRED_LOCATIONS = 16;

    MaterialCategoryDto category;

    int alarmAhead;

    int packageWidth;
    int packageHeight;
    int packageLength;
    int packageWeight;
    int netWeight;

    List<MaterialAttributeDto> attributes;
    List<UserFileDto> attachments;
    List<MaterialWarehouseOptionDto> options;
    List<MaterialPreferredLocationDto> preferredLocations;
    List<MaterialPriceDto> prices = new ArrayList<MaterialPriceDto>();

    @Override
    protected void _copy() {
        attributes = CopyUtils.copyList(attributes, this);
        attachments = new ArrayList<UserFileDto>(attachments);
        options = CopyUtils.copyList(options, this);
        preferredLocations = CopyUtils.copyList(preferredLocations, this);
        prices = CopyUtils.copyList(prices, this);
    }

    @Override
    protected void _marshal(Material source) {
        category = mref(MaterialCategoryDto.class, 0, source.getCategory());
        alarmAhead = source.getAlarmAhead();
        packageWidth = source.getPackageWidth();
        packageHeight = source.getPackageHeight();
        packageLength = source.getPackageLength();
        packageWeight = source.getPackageWeight();
        netWeight = source.getNetWeight();

        if (selection.contains(ATTRBUTES))
            attributes = marshalList(MaterialAttributeDto.class, ~MaterialAttributeDto.MATERIAL, source.getAttributes());
        else
            attributes = Collections.emptyList();

        if (selection.contains(ATTACHMENTS))
            attachments = mrefList(UserFileDto.class, source.getAttachments());
        else
            attachments = Collections.emptyList();

        if (selection.contains(OPTIONS))
            options = marshalList(MaterialWarehouseOptionDto.class, ~MaterialWarehouseOptionDto.MATERIAL,
                    source.getOptions());
        else
            options = Collections.emptyList();

        if (selection.contains(PREFERRED_LOCATIONS))
            preferredLocations = mrefList(MaterialPreferredLocationDto.class, ~MaterialPreferredLocationDto.MATERIAL,
                    source.getPreferredLocations());
        else
            preferredLocations = Collections.emptyList();

        if (selection.contains(PRICES))
            prices = mrefList(MaterialPriceDto.class, source.getPrices());
        else
            prices = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(Material target) {
        merge(target, "category", category);

        target.setPackageWidth(packageWidth);
        target.setPackageHeight(packageHeight);
        target.setPackageLength(packageLength);
        target.setPackageWeight(packageWeight);
        target.setNetWeight(netWeight);

        mergeList(target, "attributes", attributes);
        mergeList(target, "preferredLocations", preferredLocations);
        mergeList(target, "options", options);
        mergeList(target, "prices", prices);
        mergeList(target, "attachments", attachments);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public void addAttribute(MaterialAttributeDto attr) {
        if (!attributes.contains(attr))
            this.attributes.add(attr);
    }

    public void removeAttribute(MaterialAttributeDto activeAttr) {
        attributes.remove(activeAttr);
    }

    public void addOption(MaterialWarehouseOptionDto option) {
        if (!options.contains(option))
            this.options.add(option);
    }

    public void addPreferredLocation(MaterialPreferredLocationDto preferredLocation) {
        if (!preferredLocations.contains(preferredLocation))
            this.preferredLocations.add(preferredLocation);
    }

    public void addPrice(MaterialPriceDto price) {
        int existingIndex = prices.indexOf(price);
        if (existingIndex == -1)
            prices.add(0, price);
        else
            prices.set(existingIndex, price);
    }

    public void addAttachment(UserFileDto attachment) {
        if (attachment == null)
            throw new NullPointerException("attachment");
        attachments.add(attachment);
    }

    public MaterialCategoryDto getCategory() {
        return category;
    }

    public void setCategory(MaterialCategoryDto category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    public int getAlarmAhead() {
        return alarmAhead;
    }

    public void setAlarmAhead(int alarmAhead) {
        this.alarmAhead = alarmAhead;
    }

    public int getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(int packageWidth) {
        this.packageWidth = packageWidth;
    }

    public int getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(int packageHeight) {
        this.packageHeight = packageHeight;
    }

    public int getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(int packageLength) {
        this.packageLength = packageLength;
    }

    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
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

    public List<MaterialPriceDto> getPrices() {
        return prices;
    }

    public void setPrices(List<MaterialPriceDto> prices) {
        if (prices == null)
            throw new NullPointerException("prices");
        this.prices = prices;
    }

    public MaterialPriceDto getLatestPrice() {
        if (prices.isEmpty())
            return null;
        else
            return prices.get(0);
    }

    public String getCurrentPrice() {
        MaterialPriceDto materialPriceDto = getLatestPrice();
        if (materialPriceDto == null)
            return "";
        else
            return materialPriceDto.getPrice().toString();
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(getId());
    }
}
