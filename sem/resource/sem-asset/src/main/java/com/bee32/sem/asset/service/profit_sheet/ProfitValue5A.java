package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue5A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue5Assistant.buildQueryString(verified);
    }
}
