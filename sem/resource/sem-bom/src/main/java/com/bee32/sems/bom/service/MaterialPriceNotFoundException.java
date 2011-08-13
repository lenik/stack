package com.bee32.sems.bom.service;

public class MaterialPriceNotFoundException extends Exception {
    private String materialName;

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String getMessage() {
        return "不能取得物料[" + materialName + "]的单价";
    }
}
