package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue31A extends ThisYear {

    @Override
    String buildQueryString() {
        return ProfitValue31Assistant.buildQueryString(verified);
    }
}
