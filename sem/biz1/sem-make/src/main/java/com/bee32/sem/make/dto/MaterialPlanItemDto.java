package com.bee32.sem.make.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.inventory.util.IMaterialConsumer;
import com.bee32.sem.make.entity.MaterialPlanItem;
import com.bee32.sem.people.dto.PartyDto;

public class MaterialPlanItemDto
        extends MomentIntervalDto<MaterialPlanItem>
        implements IEnclosedObject<MaterialPlanDto>, IMaterialConsumer {

    private static final long serialVersionUID = 1L;

    MaterialPlanDto materialPlan;
    int index;
    MaterialDto material;
    BigDecimal quantity = new BigDecimal(0);

    PartyDto preferredSupplier;
    String additionalRequirement;

    @Override
    protected void _marshal(MaterialPlanItem source) {
        materialPlan = mref(MaterialPlanDto.class, source.getMaterialPlan());
        index = source.getIndex();
        material = mref(MaterialDto.class, source.getMaterial());
        quantity = source.getQuantity();

        preferredSupplier = mref(PartyDto.class, source.getPreferredSupplier());
        additionalRequirement = source.getAdditionalRequirement();
    }

    @Override
    protected void _unmarshalTo(MaterialPlanItem target) {
        merge(target, "materialPlan", materialPlan);
        target.setIndex(index);
        merge(target, "material", material);
        target.setQuantity(quantity);

        merge(target, "preferredSupplier", preferredSupplier);

        target.setAdditionalRequirement(additionalRequirement);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public MaterialPlanDto getEnclosingObject() {
        return getMaterialPlan();
    }

    @Override
    public void setEnclosingObject(MaterialPlanDto enclosingObject) {
        setMaterialPlan(enclosingObject);
    }

    public MaterialPlanDto getMaterialPlan() {
        return materialPlan;
    }

    public void setMaterialPlan(MaterialPlanDto materialPlan) {
        this.materialPlan = materialPlan;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public PartyDto getPreferredSupplier() {
        return preferredSupplier;
    }

    public void setPreferredSupplier(PartyDto preferredSupplier) {
        this.preferredSupplier = preferredSupplier;
    }

    @NLength(max = MaterialPlanItem.ADDITIONAL_REQUIREMENT_LENGTH)
    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = TextUtil.normalizeSpace(additionalRequirement);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(materialPlan), //
                naturalId(material));
    }

    @Override
    public void declareConsumption(ConsumptionMap consumptionMap) {
        consumptionMap.consume(material, quantity);
    }

}
