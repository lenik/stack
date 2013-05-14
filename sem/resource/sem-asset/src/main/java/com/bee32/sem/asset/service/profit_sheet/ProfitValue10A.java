package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue10A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue10Assistant.buildQueryString(verified);
    }
}
