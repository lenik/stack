package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue24A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue24Assistant.buildQueryString(verified);
    }
}
