package com.bee32.sem.account.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 票据类型
 *
 * <p lang="en">
 * Bill Type
 */
@Entity
@Blue
public class BillType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public BillType() {
        super();
    }

    public BillType(String name, String label, String description) {
        super(name, label, description);
    }

    public BillType(String name, String label) {
        super(name, label);
    }

}
