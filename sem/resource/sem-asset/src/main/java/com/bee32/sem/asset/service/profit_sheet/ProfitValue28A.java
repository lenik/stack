package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue28A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue28Assistant.buildQueryString(verified);
    }
}
