package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.sem.world.monetary.FxrRecord;

@Service
@Lazy
public class BocFxrUpdater
        extends OnlineFxrUpdater {

    @Inject
    private CommonDataManager dataManager;

    private int timeout;

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected void downloadAndCommit() {

        HttpClient httpClient = new HttpClient();

        GetMethod method = new GetMethod("http://www.boc.cn/sourcedb/whpj/index.html");
        method.addRequestHeader("Referer", "http://www.360doc.com/content/09/0911/10/61497_5827500.shtml");
        method.addRequestHeader("User-Agent",
                "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.215 Safari/534.10");
        method.getParams().setContentCharset("utf-8");
        BocFxrHtmlUtil bocStringHandler = new BocFxrHtmlUtil();
        String result = "";
        try {
            httpClient.executeMethod(method);
            result = method.getResponseBodyAsString();
            method.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();

        int count = 0;

        String bocExchangeString = bocStringHandler.preTreatment(result);
        List<String> currency_name_list = new ArrayList<String>(AvailableCurrencies.currencyCodeMap.keySet());
        Map<String, Float> exchange_map = bocStringHandler.getRateMap(currency_name_list, bocExchangeString);
        List<FxrRecord> currencyExchangeList = new ArrayList<FxrRecord>();
        for (String s : currency_name_list) {
            count++;
            CurrencyCode currency_foreign = currencyCodeDao.getCurrencyByCode(s);
            FxrRecord exchange = new FxrRecord(currency_local, currency_foreign, exchange_map.get(s), date);
            currencyExchangeList.add(exchange);
// currencyExchangeRepository.save(exchange);
            System.out.println("------------------------------------");
            System.out.println(count);
            System.out.println("------------------------------------");
        }

        exchangeRateDao.saveOrUpdateAll(currencyExchangeList);
    }

}
