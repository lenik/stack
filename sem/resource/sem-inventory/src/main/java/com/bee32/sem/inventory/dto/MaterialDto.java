package com.bee32.sem.inventory.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialXP;
import com.bee32.sem.world.color.NaturalColor;
import com.bee32.sem.world.thing.ThingDto;

public class MaterialDto
        extends ThingDto<Material, MaterialXP> {

    private static final long serialVersionUID = 1L;

    public static final int ATTRBUTES = 1;
    public static final int ATTACHMENTS = 2;
    public static final int OPTIONS = 4;
    public static final int PRICES = 8;

    MaterialCategoryDto category;
    String serial;
    String barCode;
    String modelSpec;

    NaturalColor color;

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
    protected void _marshal(Material source) {
        category = mref(MaterialCategoryDto.class, ~MaterialCategoryDto.MATERIALS, source.getCategory());
        serial = source.getSerial();
        barCode = source.getBarCode();
        modelSpec = source.getModelSpec();

        color = source.getColor();
        packageWidth = source.getPackageWidth();
        packageHeight = source.getPackageHeight();
        packageLength = source.getPackageLength();
        packageWeight = source.getPackageWeight();
        netWeight = source.getNetWeight();

        if (selection.contains(ATTRBUTES))
            attributes = marshalList(MaterialAttributeDto.class, ~MaterialAttributeDto.MATERIAL, source.getAttributes());

        preferredLocations = marshalList(MaterialPreferredLocationDto.class, ~MaterialPreferredLocationDto.MATERIAL,
                source.getPreferredLocations(), true);
        options = marshalList(MaterialWarehouseOptionDto.class, ~MaterialWarehouseOptionDto.MATERIAL,
                source.getOptions());

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments(), true);

        if (selection.contains(PRICES))
            prices = marshalList(MaterialPriceDto.class, source.getPrices(), true);

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments());
    }

    @Override
    protected void _unmarshalTo(Material target) {
        merge(target, "category", category);

        target.setSerial(serial);
        target.setBarCode(barCode);
        target.setModelSpec(modelSpec);
        target.setColor(color);
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

    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    public NaturalColor getColor() {
        return color;
    }

    public void setColor(NaturalColor color) {
        this.color = color;
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
            return "(尚无价格)";
        else
            return materialPriceDto.getPrice().getValue().toString() + "("
                    + materialPriceDto.getPrice().getCurrencyCode() + ")";
    }

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

}
