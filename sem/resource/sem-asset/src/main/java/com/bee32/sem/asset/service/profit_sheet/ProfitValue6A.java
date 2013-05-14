package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue6A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue6Assistant.buildQueryString(verified);
    }
}
