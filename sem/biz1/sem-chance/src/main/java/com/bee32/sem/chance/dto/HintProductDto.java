package com.bee32.sem.chance.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.chance.entity.HintProduct;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.dto.MaterialDto;

public class HintProductDto
    extends UIEntityDto<HintProduct, Long>
    implements IEnclosedObject<ChanceDto> {

    private static final long serialVersionUID = 1L;

    public static final int CHANCE = 1;

    public static final int ATTRIBUTES = 2;
    public static final int QUOTATIONS = 4;

    ChanceDto chance;
    String productName;
    String modelSpec;
    String unitName;
    List<HintProductAttributeDto> attributes = new ArrayList<>();
    List<HintProductQuotationDto> quotations = new ArrayList<>();
    MaterialDto decidedMaterial;

    @Override
    protected void _marshal(HintProduct source) {
        if(selection.contains(CHANCE))
            chance = mref(ChanceDto.class, source.getChance());

        productName = source.getProductName();
        modelSpec = source.getModelSpec();
        unitName = source.getUnitName();

        if (selection.contains(ATTRIBUTES))
            attributes = marshalList(HintProductAttributeDto.class, source.getAttributes());
        else
            attributes = new ArrayList<HintProductAttributeDto>();

        if (selection.contains(QUOTATIONS))
            quotations = marshalList(HintProductQuotationDto.class, source.getQuotations());
        else
            quotations = new ArrayList<HintProductQuotationDto>();

        decidedMaterial = mref(MaterialDto.class, source.getDecidedMaterial());
    }

    @Override
    protected void _unmarshalTo(HintProduct target) {
        target.setProductName(productName);
        target.setModelSpec(modelSpec);
        target.setUnitName(unitName);

        merge(target, "decidedMaterial", decidedMaterial);

        if(selection.contains(ATTRIBUTES))
            mergeList(target, "attributes", attributes);

        if(selection.contains(QUOTATIONS))
            mergeList(target, "quotations", quotations);

        merge(target, "chance", chance);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public ChanceDto getEnclosingObject() {
        return getChance();
    }

    @Override
    public void setEnclosingObject(ChanceDto enclosingObject) {
        setChance(enclosingObject);
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    @NLength(min=1, max=HintProduct.PRODUCT_NAME_LENGTH)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NLength(min=1, max=HintProduct.MODEL_SPEC_LENGTH)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    @NLength(min=1, max=HintProduct.UNIT_NAME_LENGTH)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<HintProductAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<HintProductAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public List<HintProductQuotationDto> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<HintProductQuotationDto> quotations) {
        this.quotations = quotations;
    }

    public MaterialDto getDecidedMaterial() {
        return decidedMaterial;
    }

    public void setDecidedMaterial(MaterialDto decidedMaterial) {
        this.decidedMaterial = decidedMaterial;
    }

    public HintProductQuotationDto getLastQuotation() {
        if(quotations.size() > 0) {
            Collections.sort(quotations, new ComparatorQuotationByLastModified());
            return quotations.get(0);
        }
        return null;
    }

    private class ComparatorQuotationByLastModified implements Comparator<HintProductQuotationDto> {

        @Override
        public int compare(HintProductQuotationDto o1,
                HintProductQuotationDto o2) {
            //加负号，因为要倒序
            return -o1.getLastModified().compareTo(o2.getLastModified());
        }
    }
}
