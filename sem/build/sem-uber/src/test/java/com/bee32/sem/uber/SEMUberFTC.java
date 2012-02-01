package com.bee32.sem.uber;

import java.io.IOException;
import java.util.Locale;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class SEMUberFTC
        extends FaceletsTestCase {

    static {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    protected int getRefreshPeriod() {
        return 7;
    }

    public static void main(String[] args)
            throws IOException {
        new SEMUberFTC().browseAndWait("/template/blank-rich.jsf");
    }

}
