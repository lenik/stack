package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue20B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue20Assistant.buildQueryString(verified);
    }

}
