package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue16B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue16Assistant.buildQueryString(verified);
    }

}
