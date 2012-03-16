package com.bee32.sem.make.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 工艺点
 *
 * label = 工艺点名称<br>
 * description = 工艺点描述
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "make_step_seq", allocationSize = 1)
public class MakeStep
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int EQUIPMENT_LENGTH = 2000;
    public static final int OPERATION_LENGTH = 2000;

    MakeProcess process;
    // Material input;
    Part output;
    boolean qualityControlled;

    float consumeTime;
    BigDecimal oneHourWage = BigDecimal.ZERO;
    BigDecimal otherFee = BigDecimal.ZERO;
    BigDecimal electricityFee = BigDecimal.ZERO;
    BigDecimal equipmentCost = BigDecimal.ZERO;
    Date validateTime;
    String equipment;
    String operation;
    List<MakeStepInput> inputs = new ArrayList<MakeStepInput>();

    int order;

    /**
     * 工艺的输出物料
     */
    @ManyToOne
    public Part getOutput() {
        return output;
    }

    public void setOutput(Part output) {
        if (output == null)
            throw new NullPointerException("output");
        this.output = output;
    }

    /**
     * 是否需要专检
     */
    public boolean isQualityControlled() {
        return qualityControlled;
    }

    public void setQualityControlled(boolean qualityControlled) {
        this.qualityControlled = qualityControlled;
    }

    /**
     * 消耗时间（单位:分钟）
     */
    public float getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(float consumeTime) {
        this.consumeTime = consumeTime;
    }

    /**
     * 标准小时工资
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getOneHourWage() {
        return oneHourWage;
    }

    public void setOneHourWage(BigDecimal oneHourWage) {
        this.oneHourWage = oneHourWage;
    }

    /**
     * 其它费用
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    /**
     * 电费
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        this.electricityFee = electricityFee;
    }

    /**
     * 设备费
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    /**
     * 工艺生效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    /**
     * 工艺配套设备描述
     */
    @Column(length = EQUIPMENT_LENGTH)
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    /**
     * 工艺操作描述
     */
    @Column(length = OPERATION_LENGTH)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @OneToMany
    public List<MakeStepInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<MakeStepInput> inputs) {
        if (inputs == null)
            throw new NullPointerException("inputs");
        this.inputs = inputs;
    }

    /**
     * 如果同一个part对应多个工艺，则order反应了这多个工艺的顺序
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
