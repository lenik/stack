package com.bee32.sem.bom.service;

import com.bee32.sem.inventory.entity.Material;

public class MaterialPriceNotFoundException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public MaterialPriceNotFoundException() {
        super();
    }

    public MaterialPriceNotFoundException(Material material) {
        super("不能取得物料[" + material.getName() + "]的单价");
    }

    public MaterialPriceNotFoundException(Material material, Throwable cause) {
        super("不能取得物料[" + material.getName() + "]的单价", cause);
    }

}
