package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue11A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue11Assistant.buildQueryString(verified);
    }
}
