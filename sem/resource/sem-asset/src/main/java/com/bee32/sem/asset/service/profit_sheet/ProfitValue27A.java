package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue27A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue27Assistant.buildQueryString(verified);
    }
}
