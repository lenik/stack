package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue29A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue29Assistant.buildQueryString(verified);
    }
}
