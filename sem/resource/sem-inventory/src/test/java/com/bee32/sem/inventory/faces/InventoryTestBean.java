package com.bee32.sem.inventory.faces;

import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.test.ChooseTestBean;

public class InventoryTestBean
        extends ChooseTestBean {

    private static final long serialVersionUID = 1L;

    MaterialDto material;

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public Long getMaterialId() {
        return material == null ? null : material.getId();
    }

}
