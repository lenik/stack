package com.bee32.sem.inventory.web.business;

public class SubjectNotSameException extends Exception {

    private static final long serialVersionUID = 1L;

    public SubjectNotSameException() {
        super("需要编辑的StockOrder的subejct和当前功能不匹配.");
    }
}
