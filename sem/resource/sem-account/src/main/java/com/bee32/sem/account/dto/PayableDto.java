package com.bee32.sem.account.dto;

public class PayableDto
        extends AccountReceivePayDto {

    private static final long serialVersionUID = 1L;

    public PayableDto() {
        super();
    }

    public PayableDto(int mask) {
        super(mask);
    }
}
