package com.bee32.plover.orm.util.hello.entity;

import javax.persistence.Column;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;

@SequenceGenerator(name = "idgen", sequenceName = "hello_message_seq", allocationSize = 1)
public class HelloMessage
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int MESSAGE_LENGTH = 200;

    String message;

X-Population

    @Column(length = MESSAGE_LENGTH)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
