package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue25A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue25Assistant.buildQueryString(verified);
    }
}
