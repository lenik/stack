package com.bee32.sem.world.monetary.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BocFxrHtmlUtil {

    public static String preTreatment(String s) {
        int us = s.indexOf("英镑");
        int ul = s.indexOf("卢布");
        int ue = s.indexOf("</tr>", ul);
        return s.substring(us, ue).replaceAll("\\n", "").replaceAll(" ", "").replaceAll("\\t", "")
                .replaceAll("<tdbgcolor=\"#FFFFFF\">", "").replaceAll("</td>", ",")
                .replaceAll(",</tr><tralign=\"center\">", ";");
    }

    public static String getRate(String cn, String s) {
        int iStart = s.indexOf(cn);
        int iEnd = s.indexOf(";", iStart);
        String cEx = s.substring(iStart, iEnd);
        int fn = cEx.indexOf(",",
                cEx.indexOf(",", cEx.indexOf(",", cEx.indexOf(",", cEx.indexOf(",") + 1) + 1) + 1) + 1);
        int sn = cEx.indexOf(",", fn + 1);
        return cEx.substring(fn + 1, sn);
    }

    public static Map<String, Float> getRateMap(List<String> sl, String t) {
        Map<String, Float> result = new HashMap<String, Float>();
        for (String cn : sl) {
            String rate = getRate(cn, t);
            Float dRate = Float.parseFloat(rate);
            result.put(cn, dRate);
        }
        return result;
    }

}
