package com.bee32.sem.wage;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.module.SEMModuleServer;

@Using(SEMWageUnit.class)
public class SEMWageServer
        extends SEMModuleServer {
    static {
        Locale zh_CN = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh_CN);
    }

    public static void main(String[] args)
            throws IOException {
        new SEMWageServer().startAndWait(args);
    }

}
