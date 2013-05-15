package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue12A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue12Assistant.buildQueryString(verified);
    }
}
