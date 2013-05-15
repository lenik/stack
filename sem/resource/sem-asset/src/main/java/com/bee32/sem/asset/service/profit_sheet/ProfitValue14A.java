package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue14A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue14Assistant.buildQueryString(verified);
    }
}
