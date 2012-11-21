package com.bee32.xem.zjhf.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 风机类型
 *
 * 使用父类中的label 和 description
 */
@Entity
@Table(name = "zjhf_air_blower_type")
@SequenceGenerator(name = "idgen", sequenceName = "air_blower_type_seq", allocationSize = 1)
public class AirBlowerType
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public AirBlowerType() {
        super();
    }

    public AirBlowerType(String name) {
        super(name);
    }

    public AirBlowerType(String label, String desc) {
        super();
        this.label = label;
        this.description = desc;
    }

}
