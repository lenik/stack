package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue2A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue2Assistant.buildQueryString(verified);
    }
}
