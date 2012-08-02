package com.bee32.sem.inventory;

import com.bee32.plover.site.cfg.SitePropertyPrefix;
import com.bee32.sem.term.AbstractModuleTerms;
import com.bee32.sem.term.Term;

@SitePropertyPrefix("inventory.term")
public class SEMInventoryTerms
        extends AbstractModuleTerms {

    @Term
    public String getWarehouse() {
        return getTermLabel("warehouse");
    }

    @Term
    public String getBatch1() {
        return getTermLabel("batch1");
    }

    @Term
    public String getBatch2() {
        return getTermLabel("batch2");
    }

    @Term
    public String getBatch3() {
        return getTermLabel("batch3");
    }

    @Term
    public String getBatch4() {
        return getTermLabel("batch4");
    }

    @Term
    public String getBatch5() {
        return getTermLabel("batch5");
    }

}
