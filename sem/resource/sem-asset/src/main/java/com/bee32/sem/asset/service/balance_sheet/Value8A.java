package com.bee32.sem.asset.service.balance_sheet;


public class Value8A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value8Assistant.buildQueryString(verified);
    }
}
