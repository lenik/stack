package com.bee32.xem.zjhf.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 阀类型
 *
 * 使用父类中的label 和 description
 */
@Entity
@Table(name = "zjhf_valve_type")
@SequenceGenerator(name = "idgen", sequenceName = "valve_type_seq", allocationSize = 1)
public class ValveType
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public ValveType() {
        super();
    }

    public ValveType(String name) {
        super(name);
    }

    public ValveType(String label, String desc) {
        super();
        this.label = label;
        this.description = desc;
    }

}
