package com.bee32.sem.file;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class SEMFileModuleFtc
        extends FaceletsTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMFileModuleFtc().browseAndWait(SEMFileModule.PREFIX + "/file/test.jsf");
    }

}
