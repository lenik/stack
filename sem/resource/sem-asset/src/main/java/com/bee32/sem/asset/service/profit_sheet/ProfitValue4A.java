package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue4A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue4Assistant.buildQueryString(verified);
    }
}
