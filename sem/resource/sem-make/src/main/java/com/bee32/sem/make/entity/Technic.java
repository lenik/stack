package com.bee32.sem.make.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 工艺
 *
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "technic_seq", allocationSize = 1)
public class Technic extends UIEntityAuto<Integer> implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 100;
    public static final int EQUIPMENT_LENGTH = 2000;
    public static final int OPERATION_LENGTH = 2000;

    Part part;

    String name;
    float consumeTime;
    MCValue oneHourWage = new MCValue(0);
    MCValue otherFee = new MCValue(0);
    MCValue electricityFee = new MCValue(0);
    MCValue equipmentCost = new MCValue(0);
    Date validateTime;
    String equipment;
    String operation;
    boolean check;


    List<ConsumableMaterial> consumableMaterials;


    int order;

    /**
     * 工艺对应的成品或半成品
     * @return
     */
    @ManyToOne
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    /**
     * 工艺名称
     */
    @Column(length=NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 消耗时间（单位:分钟）
     * @return
     */
    public float getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(float consumeTime) {
        this.consumeTime = consumeTime;
    }

    /**
     * 标准小时工资
     * @return
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "ohw_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "ohw")) })
    public MCValue getOneHourWage() {
        return oneHourWage;
    }

    public void setOneHourWage(MCValue oneHourWage) {
        this.oneHourWage = oneHourWage;
    }

    public void setOneHourWage(double oneHourWage) {
        setOneHourWage(new MCValue(CurrencyConfig.getNative(), oneHourWage));
    }

    /**
     * 其它费用
     * @return
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "of_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "of")) })
    public MCValue getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(MCValue otherFee) {
        this.otherFee = otherFee;
    }

    public void setOtherFee(double otherFee) {
        setOtherFee(new MCValue(CurrencyConfig.getNative(), otherFee));
    }

    /**
     * 电费
     * @return
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "ef_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "ef")) })
    public MCValue getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(MCValue electricityFee) {
        this.electricityFee = electricityFee;
    }

    public void setElectricityFee(double electricityFee) {
        setElectricityFee(new MCValue(CurrencyConfig.getNative(), electricityFee));
    }

    /**
     * 设备费
     * @return
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currencyCode", column = @Column(name = "ec_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "ec")) })
    public MCValue getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(MCValue equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public void setEquipmentCost(double equipmentCost) {
        setEquipmentCost(new MCValue(CurrencyConfig.getNative(), equipmentCost));
    }

    /**
     * 工艺生效时间
     * @return
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
     * @return
     */
    @Column(length=EQUIPMENT_LENGTH)
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    /**
     * 工艺操作描述
     * @return
     */
    @Column(length=OPERATION_LENGTH)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 是否需要专检
     * @return
     */
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @OneToMany
    public List<ConsumableMaterial> getConsumableMaterials() {
        return consumableMaterials;
    }

    public void setConsumableMaterials(List<ConsumableMaterial> consumableMaterials) {
        this.consumableMaterials = consumableMaterials;
    }

    /**
     * 如果同一个part对应多个工艺，则order反应了这多个工艺的顺序
     * @return
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
