package com.bee32.sem.make.web;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.make.dto.PartDto;

/**
 * 为Part->PartItem形成统一的树而建立的辅助类
 *
 */
public class BomTreeNode implements Serializable {
    private static final long serialVersionUID = 1L;

    Integer id;
    MaterialDto material;
    PartDto part;   //冗余part,方便ViewBean提取
    BigDecimal quantity;
    boolean makeStep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdString() {
        return id.toString();
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public boolean isMakeStep() {
        return makeStep;
    }

    public void setMakeStep(boolean makeStep) {
        this.makeStep = makeStep;
    }

}
