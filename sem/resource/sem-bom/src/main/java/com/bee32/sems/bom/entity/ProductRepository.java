package com.bee32.sems.bom.entity;

import java.util.List;

import com.bee32.plover.orm.entity.IEntityDao;
import com.bee32.sems.material.entity.MaterialImpl;

public interface ProductRepository
        extends IEntityDao<Product, Long> {

    List<Product> listPart(Integer start, Integer count);

    Product findByMaterial(MaterialImpl material);
}
