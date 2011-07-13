package com.bee32.sem.world.monetary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class BOCExchangeRateTable
        implements ExchangeRateTable {

    Map<String, Float> cache;
    long updatedDate;

    @Autowired
    static Logger logger = Logger.getLogger(BOCExchangeRateTable.class);

    @Override
    public Collection<String> getAvailableCurrencies() {
        return currencyCodeMap.keySet();
    }

    @Override
    public synchronized Float lookup(String code) {
        boolean isExpired = false;
        if (cache == null)
            isExpired = true;
        else if (System.currentTimeMillis() - updatedDate > 3600000)
            isExpired = true;

        if (isExpired) {
            try {
                cache = buildCache();
            } catch (Exception e) {
                logger.warn("Can't build the rate table: " + e.getMessage(), e);
                return null;
            }
            assert cache != null;
            updatedDate = System.currentTimeMillis();
        }

        return cache.get(code); // To do
    }

    static Map<String, Float> buildCache()
            throws Exception {
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod("http://www.boc.cn/sourcedb/whpj/index.html");
        method.addRequestHeader("Referer", "http://www.360doc.com/content/09/0911/10/61497_5827500.shtml");
        method.addRequestHeader("User-Agent",
                "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.215 Safari/534.10");

        method.getParams().setContentCharset("utf-8");

        String result;
        try {
            httpClient.executeMethod(method);
            result = method.getResponseBodyAsString();
        } catch (Exception e) {
            throw e;
        }

        BOCExchangeRateTable bocExchangeRateTable = new BOCExchangeRateTable();
        BOCStringHandler bocStringHandler = new BOCStringHandler();
        String currencyAndRate = bocStringHandler.preTreatment(result);
        System.out.print(currencyAndRate);
        List<String> cnl = new ArrayList<String>(bocExchangeRateTable.getAvailableCurrencies());
        Map<String, Float> themap = bocStringHandler.getRateMap(cnl, currencyAndRate);

        method.releaseConnection();

        return themap;
    }

    private static final Map<String, String> currencyCodeMap;

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
