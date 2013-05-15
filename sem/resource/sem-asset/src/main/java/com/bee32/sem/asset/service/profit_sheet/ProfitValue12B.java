package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue12B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue12Assistant.buildQueryString(verified);
    }

}
