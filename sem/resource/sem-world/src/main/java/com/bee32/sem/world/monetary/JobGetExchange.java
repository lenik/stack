package com.bee32.sem.world.monetary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.bee32.sems.codes.dao.CurrencyCodeDao;
import com.bee32.sems.codes.dao.ExchangeRateDao;
import com.bee32.sems.codes.entity.CurrencyCode;
import com.bee32.sems.codes.entity.ExchangeRate;

public class JobGetExchange {

// private int timeout;

    @Inject
    private ExchangeRateDao exchangeRateDao;

    @Inject
    private CurrencyCodeDao currencyCodeDao;

// public void setTimeout(int timeout) {
// this.timeout = timeout;
// }

    protected void updateExchange() {

// org.quartz.JobDetail jobDetail = jobExecutionContext.getJobDetail();

        // To do
        // in the morning 12AM of the first day of each month this blow will run;
        // 12AM is the Amazon time,what i wanted is beijing time 4AM;
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod("http://www.boc.cn/sourcedb/whpj/index.html");
        method.addRequestHeader("Referer", "http://www.360doc.com/content/09/0911/10/61497_5827500.shtml");
        method.addRequestHeader("User-Agent",
                "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.215 Safari/534.10");
        method.getParams().setContentCharset("utf-8");
        BOCStringHandler bocStringHandler = new BOCStringHandler();
        String result = "";
        try {
            httpClient.executeMethod(method);
            result = method.getResponseBodyAsString();
            method.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CurrencyCode currency_local = currencyCodeDao.getCurrencyByCode("人民币");
        Date date = new Date();

        int count = 0;

        String bocExchangeString = bocStringHandler.preTreatment(result);
        List<String> currency_name_list = new ArrayList<String>(AvailableCurrencies.currencyCodeMap.keySet());
        Map<String, Float> exchange_map = bocStringHandler.getRateMap(currency_name_list, bocExchangeString);
        List<ExchangeRate> currencyExchangeList = new ArrayList<ExchangeRate>();
        for (String s : currency_name_list) {
            count++;
            CurrencyCode currency_foreign = currencyCodeDao.getCurrencyByCode(s);
            ExchangeRate exchange = new ExchangeRate(currency_local, currency_foreign, exchange_map.get(s), date);
            currencyExchangeList.add(exchange);
// currencyExchangeRepository.save(exchange);
            System.out.println("------------------------------------");
            System.out.println(count);
            System.out.println("------------------------------------");
        }

        exchangeRateDao.saveOrUpdateAll(currencyExchangeList);
    }

}
