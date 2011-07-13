package com.bee32.sem.world.monetary;

import java.util.HashMap;
import java.util.Map;

public class AvailableCurrencies {
    public static final Map<String, String> currencyCodeMap;

    static {
        currencyCodeMap = new HashMap<String, String>();
        currencyCodeMap.put("美元", "USD");
        currencyCodeMap.put("英镑", "GBP");
        currencyCodeMap.put("日元", "JPY");
        currencyCodeMap.put("泰国铢", "THB");
        currencyCodeMap.put("韩国元", "KRW");
        currencyCodeMap.put("港币", "HKD");
        currencyCodeMap.put("新加坡元", "SGD");
        currencyCodeMap.put("瑞典克朗", "SEK");
        currencyCodeMap.put("新西兰元", "NZD");
        currencyCodeMap.put("瑞士法郎", "CHF");
        currencyCodeMap.put("丹麦克朗", "DKK");
        currencyCodeMap.put("澳大利亚元", "AUD");
        currencyCodeMap.put("欧元", "EUR");
        currencyCodeMap.put("加拿大元", "CAD");
        currencyCodeMap.put("挪威克朗", "NOK");
        currencyCodeMap.put("菲律宾比索", "PHP");
    }
}
