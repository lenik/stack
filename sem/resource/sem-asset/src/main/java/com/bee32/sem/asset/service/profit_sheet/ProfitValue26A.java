package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue26A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue26Assistant.buildQueryString(verified);
    }
}
