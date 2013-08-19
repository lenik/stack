package com.bee32.sem.world.monetary.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.util.i18n.CurrencyNames;
import com.bee32.plover.util.i18n.ILocaleAware;
import com.bee32.plover.util.i18n.ITimeZoneAware;
import com.bee32.sem.world.monetary.FxrRecord;
import com.bee32.sem.world.monetary.FxrTable;

/**
 * 中国银行汇率源
 *
 * <p lang="en">
 * BOC FXR Source
 */
public class BocFxrSource
        extends OnlineFxrSource
        implements ILocaleAware, ITimeZoneAware {

    static final Logger logger = LoggerFactory.getLogger(BocFxrSource.class);

    /**
     * HTML:
     *
     * <table width="880" border="0" cellpadding="5" cellspacing="1" bgcolor="#EAEAEA">
     * <tr>
     * <th width="86" bgcolor="#EFEFEF">货币名称</th>
     * <th width="86" bgcolor="#EFEFEF">现汇买入价</th>
     * <th width="86" bgcolor="#EFEFEF">现钞买入价</th>
     * <th width="86" bgcolor="#EFEFEF">卖出价</th>
     * <th width="86" bgcolor="#EFEFEF">基准价</th>
     * <th width="86" bgcolor="#EFEFEF">中行折算价</th>
     * <th width="86" bgcolor="#EFEFEF">发布日期</th>
     * <th width="86" bgcolor="#EFEFEF">发布时间</th>
     * </tr>
     *
     * <tr align="center">
     * <td bgcolor="#FFFFFF">英镑</td>
     * <td bgcolor="#FFFFFF">1037.83</td>
     * <td bgcolor="#FFFFFF">1005.79</td>
     * <td bgcolor="#FFFFFF">1046.17</td>
     * <td bgcolor="#FFFFFF">1044.6</td>
     * <td bgcolor="#FFFFFF">1044.6</td>
     * <td bgcolor="#FFFFFF">2011-07-17</td>
     * <td bgcolor="#FFFFFF">00:00:01</td>
     * </tr>
     * </table>
     */
    static String START_URL = "http://www.boc.cn/sourcedb/whpj/index.html";
    static Pattern SYNC_START = Pattern.compile("<th\\b.*?>货币名称</th>");
    static Currency QUOTE_CURRENCY = CNY;
    static Map<String, Currency> REV_MAP = CurrencyNames.getRevMap(zh_CN);
    static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", zh_CN);
    static {
        DATE_FORMAT.setTimeZone(TZ_PRC);
    }

    static final int F_CNAME = 0;
    static final int F_BUYING_RATE = 1;
    static final int F_XBUYING_RATE = 2;
    static final int F_SELLING_RATE = 3;
    static final int F_BASE_RATE = 4;
    static final int F_EVAL_RATE = 5;
    static final int F_DATE = 6;
    static final int F_TIME = 7;

    @Override
    public FxrTable download()
            throws IOException {
        String html = httpGet(START_URL);
        if (html == null)
            return null;

        FxrTable table = new FxrTable(QUOTE_CURRENCY);

        Matcher matcher = SYNC_START.matcher(html);
        if (!matcher.find()) {
            logger.warn("Failed to sync to the start part of BOC FXR html");
            return null;
        }

        html = html.substring(matcher.start());
        StringReader reader = new StringReader(html);
        BufferedReader in = new BufferedReader(reader);

        FxrRecord record = null;
        String line;
        int columnIndex = 0;
        while ((line = in.readLine()) != null) {
            line = line.trim().toLowerCase();

            if (line.startsWith("</table>"))
                break;

            if (line.startsWith("<tr>") || line.startsWith("<tr ")) {
                record = new FxrRecord();
                columnIndex = 0;
                continue;
            }

            if (line.startsWith("</tr>")) {
                if (record != null) {
                    // logger.debug("FXR Record: " + record);
                    table.put(record);
                }
                record = null;
                continue;
            }

            if (line.startsWith("<td>") || line.startsWith("<td ")) {
                String text;
                int startClose = line.indexOf('>');
                int endOpen = line.indexOf('<', startClose + 1);
                text = line.substring(startClose + 1, endOpen);

                if (record != null) {
                    switch (columnIndex) {
                    case F_CNAME:
                        Currency unitCurrency = REV_MAP.get(text);
                        if (unitCurrency == null) {
                            logger.warn("Unknown currency name (locale): " + text);
                            record = null;
                            continue;
                        }
                        record.setUnitCurrency(unitCurrency);
                        break;

                    case F_BUYING_RATE:
                        Float buyingRate = parseScale(text);
                        if (buyingRate == null) {
                            logger.warn("No buying rate, skipped: " + record.getUnitCurrency());
                            record = null;
                            continue;
                        }
                        record.setBuyingRate(buyingRate);
                        break;

                    case F_XBUYING_RATE:
                        Float xbuyingRate = parseScale(text);
                        record.setXbuyingRate(xbuyingRate);
                        break;

                    case F_SELLING_RATE:
                        Float sellingRate = parseScale(text);
                        if (sellingRate == null) {
                            logger.warn("No selling rate, skipped: " + record.getUnitCurrency());
                            record = null;
                            continue;
                        }
                        record.setSellingRate(sellingRate);
                        break;

                    case F_BASE_RATE:
                        Float baseRate = parseScale(text);
                        record.setBaseRate(baseRate);
                        break;

                    case F_EVAL_RATE:
                        if (record.getBaseRate() == null) {
                            // Mostly they are the same.
                            Float evalRate = parseScale(text);
                            record.setBaseRate(evalRate);
                        }
                        break;

                    case F_DATE:
                        Date date = parseDate(text);
                        record.setDate(date);
                        break;

                    case F_TIME:
                        // Not used.
                        break;
                    } // columnIndex
                } // record != null
                columnIndex++;
            }
        }

        return table;
    }

    Float parseScale(String text) {
        if (text == null)
            throw new NullPointerException("text");

        text = text.replace("&nbsp;", " ");
        text = text.trim();

        if (text.isEmpty())
            return null;
        float value = Float.parseFloat(text);
        value /= 100.0;
        return value;
    }

    Date parseDate(String text) {
        Date date;
        try {
            date = DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return date;
    }

}
