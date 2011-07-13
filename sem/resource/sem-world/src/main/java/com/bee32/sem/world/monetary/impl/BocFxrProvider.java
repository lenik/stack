package com.bee32.sem.world.monetary.impl;

import java.util.Currency;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.world.monetary.IFxrProvider;

/**
 * 中国银行提供的外汇查询。
 */
public class BocFxrProvider
        implements IFxrProvider {

    static Logger logger = LoggerFactory.getLogger(BocFxrProvider.class);

    Map<String, Double> cache;
    long updatedDate;

    @Override
    public synchronized Double lookup(Currency code) {
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

    static Map<String, Double> buildCache()
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

        BocFxrProvider bocExchangeRateTable = new BocFxrProvider();
        String currencyAndRate = BocFxrHtmlUtil.preTreatment(result);
        System.out.print(currencyAndRate);

        Map<String, Float> map = BocFxrHtmlUtil.getRateMap(currencyAndRate);

        method.releaseConnection();

        return map;
    }

}
