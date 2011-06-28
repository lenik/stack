package com.bee32.icsf.access.flatacl;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class FlatACL
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public FlatACL() {
        super();
    }

    public FlatACL(String name) {
        super(name);
    }

}
