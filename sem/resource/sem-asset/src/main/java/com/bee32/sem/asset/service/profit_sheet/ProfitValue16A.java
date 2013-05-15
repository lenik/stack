package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue16A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue16Assistant.buildQueryString(verified);
    }
}
