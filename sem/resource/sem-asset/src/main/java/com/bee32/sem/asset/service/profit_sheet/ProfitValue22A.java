package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue22A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue22Assistant.buildQueryString(verified);
    }
}
