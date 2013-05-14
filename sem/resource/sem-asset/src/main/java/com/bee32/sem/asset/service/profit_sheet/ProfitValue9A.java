package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue9A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue9Assistant.buildQueryString(verified);
    }
}
