package com.bee32.plover.orm.util.hello.entity;

import com.bee32.plover.orm.entity.EntityAuto;

public class HelloMessage
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
