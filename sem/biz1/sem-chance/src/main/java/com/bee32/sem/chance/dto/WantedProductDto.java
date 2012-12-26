package com.bee32.sem.chance.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.orm.util.dto.LastModifiedComparator;
import com.bee32.sem.chance.entity.WantedProduct;
import com.bee32.sem.chance.entity.WantedProductXP;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.world.thing.ThingDto;

public class WantedProductDto
        extends ThingDto<WantedProduct, WantedProductXP>
        implements IEnclosedObject<ChanceDto> {

    private static final long serialVersionUID = 1L;

    public static final int ATTRIBUTES = 1;
    public static final int QUOTATIONS = 2;

    ChanceDto chance;
    List<WantedProductAttributeDto> attributes = new ArrayList<>();
    List<WantedProductQuotationDto> quotations = new ArrayList<>();
    MaterialDto decidedMaterial;

    @Override
    protected void _copy() {
        super._copy();
        attributes = CopyUtils.copyList(attributes, this);
        quotations = CopyUtils.copyList(quotations, this);
    }

    @Override
    protected void _marshal(WantedProduct source) {
        chance = mref(ChanceDto.class, source.getChance());

        if (selection.contains(ATTRIBUTES))
            attributes = marshalList(WantedProductAttributeDto.class, source.getAttributes());
        else
            attributes = Collections.emptyList();

        if (selection.contains(QUOTATIONS))
            quotations = marshalList(WantedProductQuotationDto.class, source.getQuotations());
        else
            quotations = Collections.emptyList();

        decidedMaterial = mref(MaterialDto.class, source.getDecidedMaterial());
    }

    @Override
    protected void _unmarshalTo(WantedProduct target) {
        merge(target, "chance", chance);

        if (selection.contains(ATTRIBUTES))
            mergeList(target, "attributes", attributes);

        if (selection.contains(QUOTATIONS))
            mergeList(target, "quotations", quotations);

        merge(target, "decidedMaterial", decidedMaterial);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
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

    @NLength(min = 1, max = WantedProduct.LABEL_LENGTH)
    public String getProductName() {
        return getLabel();
    }

    public void setProductName(String productName) {
        setLabel(productName);
    }

    public List<WantedProductAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<WantedProductAttributeDto> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    public List<WantedProductQuotationDto> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<WantedProductQuotationDto> quotations) {
        if (quotations == null)
            throw new NullPointerException("quotations");
        this.quotations = quotations;
    }

    public MaterialDto getDecidedMaterial() {
        return decidedMaterial;
    }

    public void setDecidedMaterial(MaterialDto decidedMaterial) {
        this.decidedMaterial = decidedMaterial;
    }

    public WantedProductQuotationDto getLastQuotation() {
        if (quotations.isEmpty())
            return null;
        Collections.sort(quotations, LastModifiedComparator.INSTANCE);
        return quotations.get(quotations.size() - 1);
    }

}
