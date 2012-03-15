package com.bee32.sem.make.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 工艺
 *
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "technic_seq", allocationSize = 1)
public class Technic extends UIEntityAuto<Integer> implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Part part;
}
