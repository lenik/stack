package com.bee32.sem.make.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;

/**
 * 消耗材料
 *
 * 工艺中的消耗材料。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "make_step_input_seq", allocationSize = 1)
public class MakeStepInput
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeStepModel stepModel;

    Material material;
    BigDecimal quantity = new BigDecimal(0);

    /**
     * 工艺
     *
     * 消耗材料对应的工艺步骤。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public MakeStepModel getStepModel() {
        return stepModel;
    }

    public void setStepModel(MakeStepModel stepModel) {
        if (stepModel == null)
            throw new NullPointerException("stepModel");
        this.stepModel = stepModel;
    }

    /**
     * 物料
     *
     * 消耗材料对应的物料。
     */
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    /**
     * 数量
     *
     * 相应工艺中需要消耗材料的数量。
     *
     * @return
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    public void setQuantity(long quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(double quantity) {
        setQuantity(new BigDecimal(quantity));
    }

}
