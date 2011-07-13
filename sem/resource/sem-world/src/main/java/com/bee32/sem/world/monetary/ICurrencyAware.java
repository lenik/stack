package com.bee32.sem.world.monetary;

import java.util.Currency;

public interface ICurrencyAware {

    /** 澳大利亚元 */
    Currency AUD = Currency.getInstance("AUD");

    /** 加拿大元 */
    Currency CAD = Currency.getInstance("CAD");

    /** 瑞士法郎 */
    Currency CHF = Currency.getInstance("CHF");

    /** 人民币 */
    Currency CNY = Currency.getInstance("CNY");

    /** 丹麦克朗 */
    Currency DKK = Currency.getInstance("DKK");

    /** 欧元 */
    Currency EUR = Currency.getInstance("EUR");

    /** 英镑 */
    Currency GBP = Currency.getInstance("GBP");

    /** 港币 */
    Currency HKD = Currency.getInstance("HKD");

    /** 日元 */
    Currency JPY = Currency.getInstance("JPY");

    /** 韩国元 */
    Currency KRW = Currency.getInstance("KRW");

    /** 挪威克朗 */
    Currency NOK = Currency.getInstance("NOK");

    /** 新西兰元 */
    Currency NZD = Currency.getInstance("NZD");

    /** 菲律宾比索 */
    Currency PHP = Currency.getInstance("PHP");

    /** 瑞典克朗 */
    Currency SEK = Currency.getInstance("SEK");

    /** 新加坡元 */
    Currency SGD = Currency.getInstance("SGD");

    /** 泰国铢 */
    Currency THB = Currency.getInstance("THB");

    /** 美元 */
    Currency USD = Currency.getInstance("USD");

    Currency DEFAULT_CURRENCY = CurrencyConfig.DEFAULT;

}
