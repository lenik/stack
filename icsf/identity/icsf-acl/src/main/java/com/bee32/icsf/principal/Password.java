package com.bee32.icsf.principal;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Password
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    User user;
    String password;

}
