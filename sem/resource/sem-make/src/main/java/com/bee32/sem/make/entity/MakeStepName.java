package com.bee32.sem.make.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 标准工艺名称
 *
 * 企业中所有的工艺可以给予一个标准备的工艺名称来命名。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_name_seq", allocationSize = 1)
public class MakeStepName
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

}
