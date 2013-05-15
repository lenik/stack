package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue15A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue15Assistant.buildQueryString(verified);
    }
}
