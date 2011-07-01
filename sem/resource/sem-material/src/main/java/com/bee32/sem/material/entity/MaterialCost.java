package com.bee32.sem.material.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityAuto;

@Entity
public class MaterialCost
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    // 材料成本
    double materialCost;

    // 准备成本
    double settingUpCost;

    // 人工成本
    double labourCost;

    // 变动制造费用
    double variableFactoryOverhead;

    // 固定制造费用
    double fixedFactoryOverhead;

    // 外协加工费用
    double outsourceCost;

}
