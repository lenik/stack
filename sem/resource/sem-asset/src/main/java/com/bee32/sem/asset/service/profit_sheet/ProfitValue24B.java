package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue24B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue24Assistant.buildQueryString(verified);
    }

}
