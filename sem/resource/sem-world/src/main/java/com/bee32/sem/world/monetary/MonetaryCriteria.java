package com.bee32.sem.world.monetary;

import java.util.Currency;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class MonetaryCriteria
        extends CriteriaSpec {

    public static CriteriaElement currencyOf(String property, Currency currency) {
        return equals(property, currency.getCurrencyCode());
    }

    public static CriteriaElement unitCurrencyOf(Currency currency) {
        return currencyOf("_unit", currency);
    }

}
