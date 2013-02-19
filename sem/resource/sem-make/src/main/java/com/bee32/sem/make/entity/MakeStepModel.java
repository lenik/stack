package com.bee32.sem.make.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 工艺
 *
 * 产品在设置BOM后，在BOM上的成品和半成品上，可以设置工艺。
 *
 * label = 工艺名称<br>
 * description = 工艺描述
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "make_step_seq", allocationSize = 1)
public class MakeStepModel
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int EQUIPMENT_LENGTH = 2000;
    public static final int OPERATION_LENGTH = 2000;

    MakeStepName stepName;
    int order;
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
    QCSpec qcSpec = new QCSpec();

    /**
     * 工艺名称
     *
     * 对应标准工艺名称。
     *
     * @return
     */
    @ManyToOne
    public MakeStepName getStepName() {
        return stepName;
    }

    public void setStepName(MakeStepName stepName) {
        if (stepName == null)
            throw new NullPointerException("stepName");
        this.stepName = stepName;
    }

    /**
     * 工艺顺序
     *
     * 如果同一个part对应多个工艺，则order反应了这多个工艺的顺序。
     */
    @NaturalId
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 成品/半成品
     *
     * 本工艺对应的成品或半成品。即本工艺完成后，会生产出什么成品或半成品。
     */
    @NaturalId
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
     * 是否专检
     *
     * 本工艺是否需要专检。
     */
    public boolean isQualityControlled() {
        return qualityControlled;
    }

    public void setQualityControlled(boolean qualityControlled) {
        this.qualityControlled = qualityControlled;
    }

    /**
     * 消耗时间
     *
     * 完成本工艺需要消耗的时间（单位:分钟）。
     */
    public float getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(float consumeTime) {
        this.consumeTime = consumeTime;
    }

    /**
     * 标准小时工资
     *
     * 在本工艺工作的工人的小时工资。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getOneHourWage() {
        return oneHourWage;
    }

    public void setOneHourWage(BigDecimal oneHourWage) {
        this.oneHourWage = oneHourWage;
    }

    /**
     * 其它费用
     *
     * 完成本工艺的其它费用。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    /**
     * 电费
     *
     * 完成本工艺需要使用的电的费用。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        this.electricityFee = electricityFee;
    }

    /**
     * 设备费
     *
     * 完成本工艺使用设备所需的花费。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    /**
     * 工艺生效时间
     *
     * 本工艺的实际完成所需的时间。工艺的准备工作所花的时间不计算在内。
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    /**
     * 设备
     *
     * 工艺配套设备描述。
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
     *
     * 完成本工艺的具体操作描述。
     */
    @Column(length = OPERATION_LENGTH)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 消耗物料
     *
     * 完成本工艺需要消耗的物料列表。
     *
     * @return
     */
    @OneToMany(orphanRemoval = true, mappedBy="stepModel")
    @Cascade(CascadeType.ALL)
    public List<MakeStepInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<MakeStepInput> inputs) {
        if (inputs == null)
            throw new NullPointerException("inputs");
        this.inputs = inputs;
    }

    /**
     * 质量
     *
     * 本工艺的质检标准。
     *
     * @return
     */
    @OneToOne(orphanRemoval=true)
    @Cascade(CascadeType.SAVE_UPDATE)
    public QCSpec getQcSpec() {
        return qcSpec;
    }

    public void setQcSpec(QCSpec qcSpec) {
        this.qcSpec = qcSpec;
    }

}
