package com.bee32.sem.make.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.MakeStepInput;
import com.bee32.sem.material.dto.MaterialDto;

public class MakeStepInputDto
    extends UIEntityDto<MakeStepInput, Integer>
    implements IEnclosedObject<MakeStepModelDto>{

    private static final long serialVersionUID = 1L;

    MakeStepModelDto stepModel;

    MaterialDto material;
    BigDecimal quantity = new BigDecimal(0);


    @Override
    protected void _marshal(MakeStepInput source) {
        stepModel = mref(MakeStepModelDto.class, source.getStepModel());
        material = mref(MaterialDto.class, source.getMaterial());
        quantity = source.getQuantity();
    }

    @Override
    protected void _unmarshalTo(MakeStepInput target) {
        merge(target, "stepModel", stepModel);

        merge(target, "material", material);
        target.setQuantity(quantity);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public MakeStepModelDto getStepModel() {
        return stepModel;
    }

    public void setStepModel(MakeStepModelDto stepModel) {
        if (stepModel == null)
            throw new NullPointerException("stepModel");
        this.stepModel = stepModel;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(long quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(double quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    @Override
    public MakeStepModelDto getEnclosingObject() {
        return getStepModel();
    }

    @Override
    public void setEnclosingObject(MakeStepModelDto enclosingObject) {
        setStepModel(enclosingObject);

    }


}
