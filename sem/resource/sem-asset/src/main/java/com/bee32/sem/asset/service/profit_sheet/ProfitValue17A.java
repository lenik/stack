package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue17A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue17Assistant.buildQueryString(verified);
    }
}
