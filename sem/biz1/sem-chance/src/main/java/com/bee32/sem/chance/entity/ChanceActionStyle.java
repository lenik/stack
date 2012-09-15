package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 洽谈类型
 *
 * 行动日志发生时，和客户所产用的交流方式
 *
 */
@Entity
@Blue
public class ChanceActionStyle
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceActionStyle() {
        super();
    }

    public ChanceActionStyle(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceActionStyle(String name, String label) {
        super(name, label);
    }

}
