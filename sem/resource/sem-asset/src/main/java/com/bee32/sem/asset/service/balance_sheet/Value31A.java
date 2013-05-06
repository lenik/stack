package com.bee32.sem.asset.service.balance_sheet;


public class Value31A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value31Assistant.buildQueryString(verified);
    }
}
