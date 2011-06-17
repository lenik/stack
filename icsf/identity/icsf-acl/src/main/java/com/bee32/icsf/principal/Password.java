package com.bee32.icsf.principal;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

@Entity
@Blue
public class Password
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    User user;
    String password;

}
