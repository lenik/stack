package com.bee32.sem.make.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.MakeStepModel;

public class MakeStepModelDto
    extends UIEntityDto<MakeStepModel, Integer>  {

    private static final long serialVersionUID = 1L;

    public static final int INPUTS = 1;

    MakeStepNameDto stepName;
    int order;
    PartDto output;
    boolean qualityControlled;

    float consumeTime;
    BigDecimal oneHourWage = BigDecimal.ZERO;
    BigDecimal otherFee = BigDecimal.ZERO;
    BigDecimal electricityFee = BigDecimal.ZERO;
    BigDecimal equipmentCost = BigDecimal.ZERO;
    Date validateTime;
    String equipment;
    String operation;

    List<MakeStepInputDto> inputs;
    QCSpecDto qcSpec;

    @Override
    protected void _marshal(MakeStepModel source) {
        stepName = mref(MakeStepNameDto.class, source.getStepName());
        order = source.getOrder();
        output = mref(PartDto.class, source.getOutput());
        qualityControlled = source.isQualityControlled();

        consumeTime = source.getConsumeTime();
        oneHourWage = source.getOneHourWage();
        otherFee = source.getOtherFee();
        electricityFee = source.getElectricityFee();
        equipmentCost = source.getEquipmentCost();
        validateTime = source.getValidateTime();
        equipment = source.getEquipment();
        operation = source.getOperation();

        if (selection.contains(INPUTS))
            inputs = marshalList(MakeStepInputDto.class, source.getInputs());
        else
            inputs = new ArrayList<MakeStepInputDto>();

        qcSpec = marshal(QCSpecDto.class, source.getQcSpec());

    }

    @Override
    protected void _unmarshalTo(MakeStepModel target) {
        merge(target, "stepName", stepName);
        target.setOrder(order);
        merge(target, "output", output);
        target.setQualityControlled(qualityControlled);

        target.setConsumeTime(consumeTime);
        target.setOneHourWage(oneHourWage);
        target.setOtherFee(otherFee);
        target.setElectricityFee(electricityFee);
        target.setEquipmentCost(equipmentCost);
        target.setValidateTime(validateTime);
        target.setEquipment(equipment);
        target.setOperation(operation);

        if (selection.contains(INPUTS))
            mergeList(target, "inputs", inputs);

        merge(target, "qcSpec", qcSpec);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeStepNameDto getStepName() {
        return stepName;
    }

    public void setStepName(MakeStepNameDto stepName) {
        this.stepName = stepName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PartDto getOutput() {
        return output;
    }

    public void setOutput(PartDto output) {
        this.output = output;
    }

    public boolean isQualityControlled() {
        return qualityControlled;
    }

    public void setQualityControlled(boolean qualityControlled) {
        this.qualityControlled = qualityControlled;
    }

    public float getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(float consumeTime) {
        this.consumeTime = consumeTime;
    }

    public BigDecimal getOneHourWage() {
        return oneHourWage;
    }

    public void setOneHourWage(BigDecimal oneHourWage) {
        this.oneHourWage = oneHourWage;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    @NLength(max = MakeStepModel.EQUIPMENT_LENGTH)
    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        this.electricityFee = electricityFee;
    }

    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @NLength(max = MakeStepModel.OPERATION_LENGTH)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<MakeStepInputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<MakeStepInputDto> inputs) {
        this.inputs = inputs;
    }

    public QCSpecDto getQcSpec() {
        return qcSpec;
    }

    public void setQcSpec(QCSpecDto qcSpec) {
        this.qcSpec = qcSpec;
    }

}
