package com.bee32.sems.bom.entity;

import java.util.List;

import com.bee32.plover.orm.entity.IEntityDao;

public interface ComponentRepository
        extends IEntityDao<Component, Long> {

    List<Component> listByProduct(Long productId);

}
