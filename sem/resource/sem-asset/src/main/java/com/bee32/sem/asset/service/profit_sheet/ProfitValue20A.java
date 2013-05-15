package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue20A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue20Assistant.buildQueryString(verified);
    }
}
