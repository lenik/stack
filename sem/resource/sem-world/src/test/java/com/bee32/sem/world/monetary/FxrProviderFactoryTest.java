package com.bee32.sem.world.monetary;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.bee32.plover.test.TestSupport;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.misc.i18n.ITimeZoneAware;

public class FxrProviderFactoryTest
        extends TestSupport
        implements ICurrencyAware {

    @Override
    protected TimeZone getTimeZone() {
        return ITimeZoneAware.TZ_PRC;
    }

    @Test
    public void testGetTestFxp()
            throws FxrQueryException {
        IFxrProvider fxp = FxrProviderFactory.getFxrProvider();
        Date date = parseDate("2011-7-25");
        float fxr = fxp.getFxr(date, USD, FxrUsage.MIDDLE);

        // 6.442655
        assertEqualsX(6.442655f, fxr);
    }

}
