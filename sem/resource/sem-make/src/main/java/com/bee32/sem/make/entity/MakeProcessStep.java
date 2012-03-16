package com.bee32.sem.make.entity;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 工序
 *
 * label/description: 工序名称/工序描述
 */
public class MakeProcessStep
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    MakeProcess process;
    int order;

}
