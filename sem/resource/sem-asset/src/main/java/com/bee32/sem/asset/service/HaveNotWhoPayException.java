package com.bee32.sem.asset.service;

public class HaveNotWhoPayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "没有付款人!";
    }

}
