package com.bee32.sem.chance.entity;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;

/**
 * 选型明细
 *
 */
public class LectotypeItem
    extends UIEntityAuto<Long>
    implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    String name;
    String spec;
    String unit;

    Material material;


}
