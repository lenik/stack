package com.bee32.sem.world.monetary;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.util.i18n.CurrencyConfig;

public class MonetaryDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<SelectItem> currencySelectItems;

    public List<SelectItem> getCurrencySelectItems() {
        if (currencySelectItems == null) {
            synchronized (this) {
                if (currencySelectItems == null) {
                    currencySelectItems = new ArrayList<SelectItem>();
                    for (Currency c : CurrencyConfig.list()) {
                        SelectItem item = new SelectItem();
                        item.setLabel(CurrencyConfig.format(c));
                        item.setValue(c.getCurrencyCode());
                        currencySelectItems.add(item);
                    }
                }
            }
        }
        return currencySelectItems;
    }

}
