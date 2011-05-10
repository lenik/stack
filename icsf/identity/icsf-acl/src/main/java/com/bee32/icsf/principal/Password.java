package com.bee32.icsf.principal;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.BlueEntity;

@Entity
public class Password
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    User user;
    String password;

}
