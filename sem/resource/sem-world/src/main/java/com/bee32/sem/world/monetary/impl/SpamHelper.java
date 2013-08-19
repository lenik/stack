package com.bee32.sem.world.monetary.impl;

import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 垃圾邮件辅助工具
 *
 * <p lang="en">
 * Spam Helper
 */
public class SpamHelper {

    static String AGENT_MOZILLA = "Mozilla/5.0 (X11; U; Linux i686; en-US)";
    static String AGENT_WEBKIT = "AppleWebKit/534.10 (KHTML, like Gecko)";
    static String AGENT_CHROME = "Chrome/8.0.552.215";
    static String AGENT_SAFARI = "Safari/534.10";

    public static String getReferer(String prefix) {
        return "http://www.360doc.com/content/09/0911/10/61497_5827500.shtml";
    }

    public static String getAgent() {
        return getAgent(AGENT_MOZILLA, AGENT_WEBKIT, AGENT_CHROME, AGENT_SAFARI);
    }

    public static String getAgent(String... list) {
        StringBuilder buf = new StringBuilder(200);
        for (int i = 0; i < list.length; i++) {
            if (i != 0)
                buf.append(' ');
            buf.append(list[i]);
        }
        return buf.toString();
    }

    public static GetMethod prepareGet(String uri) {
        GetMethod getMethod = new GetMethod(uri);
        getMethod.addRequestHeader("Referer", SpamHelper.getReferer(null));
        getMethod.addRequestHeader("User-Agent", SpamHelper.getAgent());
        getMethod.getParams().setContentCharset("utf-8");
        return getMethod;
    }

}
