package com.bee32.plover.arch.logging;

import javax.free.PrefixMap;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class PrefixPatternLayout
        extends PatternLayout {

    static PrefixMap<String> packageMap;
    static {
        packageMap = new PrefixMap<String>();
        packageMap.put("com.bee32.", "BEE32");
        packageMap.put("com.bee32.plover.", "PLOVER");
        packageMap.put("com.bee32.icsf.", "ICSF");
        packageMap.put("org.mortbay.", "JETTY");
        packageMap.put("org.springframework.", "SPRING");
        packageMap.put("org.hibernate.", "HIBER");
        packageMap.put("org.apache.myfaces.", "JSF");
        packageMap.put("org.primefaces.", "JSF/PF");
    }

    @Override
    public String format(LoggingEvent event) {
        String s = super.format(event);

        String name = event.getLoggerName();
        String pack = packageMap.floor(name);
        if (pack != null) {
            if (s.startsWith("["))
                s = "[" + pack + ":" + s.substring(1);
            else
                s = "[" + pack + "] " + s;
        }

        return s;
    }

}
