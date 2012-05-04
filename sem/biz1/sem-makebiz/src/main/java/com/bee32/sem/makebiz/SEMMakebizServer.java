package com.bee32.sem.makebiz;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.module.SEMModuleServer;

@Using(SEMMakebizUnit.class)
public class SEMMakebizServer
        extends SEMModuleServer {

    static {
        Locale zh_CN = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh_CN);
    }

    public static void main(String[] args)
            throws IOException {
        new SEMMakebizServer().startAndWait(args);
    }

}
