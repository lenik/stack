package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue18A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue18Assistant.buildQueryString(verified);
    }
}
