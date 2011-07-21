package com.bee32.sem.inventory.web.dialogs;

import java.io.Serializable;

import com.bee32.sem.inventory.dto.MaterialDto;

public class MaterialDialogModel implements Serializable {

    private static final long serialVersionUID = 1L;

    MaterialDto material;

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

}
