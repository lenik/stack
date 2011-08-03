package com.bee32.sem.world.monetary;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.sem.misc.i18n.CurrencyConfig;

public class CurrencyUtil {

    public static List<SelectItem> selectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Currency c : CurrencyConfig.list()) {
            SelectItem item = new SelectItem();
            item.setLabel(CurrencyConfig.format(c));
            item.setValue(c.getCurrencyCode());
            selectItems.add(item);
        }
        return selectItems;
    }

}
