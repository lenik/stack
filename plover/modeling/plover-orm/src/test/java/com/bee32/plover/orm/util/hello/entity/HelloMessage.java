package com.bee32.plover.orm.util.hello.entity;

import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.Eauto;

@SequenceGenerator(name = "idgen", sequenceName = "hello_message_seq", allocationSize = 1)
public class HelloMessage
        extends Eauto<Integer> {

    private static final long serialVersionUID = 1L;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
