package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue8A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue8Assistant.buildQueryString(verified);
    }
}
