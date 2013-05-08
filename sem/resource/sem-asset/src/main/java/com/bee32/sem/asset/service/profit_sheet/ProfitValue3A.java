package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue3A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue3Assistant.buildQueryString(verified);
    }
}
