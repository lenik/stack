package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.bee32.plover.test.TestSupport;
import com.bee32.plover.util.i18n.ICurrencyAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

public class AbstractOrderItemTest
        extends TestSupport
        implements ICurrencyAware {

    static class Item
            extends AbstractItem {

        private static final long serialVersionUID = 1L;

        Date fxrDate;

        public Item(Date fxrDate) {
            this.fxrDate = fxrDate;
        }

        @Override
        protected Date getFxrDate() {
            return fxrDate;
        }

    }

    @Test
    public void testTotal()
            throws FxrQueryException {
        float fxrUSD = 6.442655f;

        Item item = new Item(parseDate("2011-7-25"));
        item.setQuantity(10);
        item.setPrice(new MCValue(USD, 1));

        MCValue total = item.getTotal();
        assertEquals(USD, total.getCurrency());
        assertEquals(new BigDecimal(10L), total.getValue());

        assertEqualsX(fxrUSD, item.getNativePrice().floatValue());
        assertEqualsX(fxrUSD * 10, item.getNativeTotal().floatValue());
    }

}
