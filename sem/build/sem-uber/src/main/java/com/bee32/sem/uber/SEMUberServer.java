package com.bee32.sem.uber;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.module.SEMModuleServer;

@Using(SEMUberUnit.class)
public class SEMUberServer
        extends SEMModuleServer {

    static {
        Locale zh_CN = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh_CN);
    }

    public static void main(String[] args)
            throws IOException {
        new SEMUberServer().startAndWait(args);
    }

}
