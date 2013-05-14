package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue7A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue7Assistant.buildQueryString(verified);
    }
}
