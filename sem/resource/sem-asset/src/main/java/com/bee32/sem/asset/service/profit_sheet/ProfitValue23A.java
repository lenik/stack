package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue23A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue23Assistant.buildQueryString(verified);
    }
}
