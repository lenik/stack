package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue13A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue13Assistant.buildQueryString(verified);
    }
}
