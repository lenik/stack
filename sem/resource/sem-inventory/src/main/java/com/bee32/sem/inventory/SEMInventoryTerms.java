package com.bee32.sem.inventory;

import com.bee32.plover.site.cfg.SitePropertyPrefix;
import com.bee32.sem.module.AbstractModuleTerms;
import com.bee32.sem.module.Term;

@SitePropertyPrefix("inventory.term")
public class SEMInventoryTerms
        extends AbstractModuleTerms {

    @Term
    public String getWarehouse() {
        return getProperty("warehouse");
    }

    public void setWarehouse(String warehouse) {
        setProperty("warehouse", warehouse);
    }

    @Term
    public String getBatch1() {
        return getProperty("batch1");
    }

    public void setBatch1(String batch1) {
        setProperty("batch1", batch1);
    }

    @Term
    public String getBatch2() {
        return getProperty("batch2");
    }

    public void setBatch2(String batch2) {
        setProperty("batch2", batch2);
    }

    @Term
    public String getBatch3() {
        return getProperty("batch3");
    }

    public void setBatch3(String batch3) {
        setProperty("batch3", batch3);
    }

    @Term
    public String getBatch4() {
        return getProperty("batch4");
    }

    public void setBatch4(String batch4) {
        setProperty("batch4", batch4);
    }

    @Term
    public String getBatch5() {
        return getProperty("batch5");
    }

    public void setBatch5(String batch5) {
        setProperty("batch5", batch5);
    }

}
