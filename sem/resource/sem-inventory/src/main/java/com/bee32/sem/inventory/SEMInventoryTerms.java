package com.bee32.sem.inventory;

import com.bee32.plover.site.cfg.SitePropertyPrefix;
import com.bee32.sem.term.AbstractModuleTerms;
import com.bee32.sem.term.Term;

/**
 *
 *
 * <p lang="en">
 */
@SitePropertyPrefix("inventory.term")
public class SEMInventoryTerms
        extends AbstractModuleTerms {

    /**
     * 仓库
     *
     * 用于出入库统计的最大分组属性。
     * <p lang="en">
     * Warehouse
     *
     * The maximum grouping category attribute for in/out operations.
     */
    @Term
    public String getWarehouse() {
        return getTermLabel("warehouse");
    }

    /**
     * 编组1
     *
     * 用于统计的自定义批号属性 （1)
     * <p lang="en">
     * Batch 1
     *
     * Custom grouping category (1)
     */
    @Term
    public String getBatch1() {
        return getTermLabel("batch1");
    }

    /**
     * 编组2
     *
     * 用于统计的自定义批号属性 （2)
     *
     * <p lang="en">
     * Batch 2
     *
     * Custom grouping category (2)
     */
    @Term
    public String getBatch2() {
        return getTermLabel("batch2");
    }

    /**
     * 编组3
     *
     * 用于统计的自定义批号属性 （3)
     *
     * <p lang="en">
     * Batch 3
     *
     * Custom grouping category (3)
     */
    @Term
    public String getBatch3() {
        return getTermLabel("batch3");
    }

    /**
     * 编组4
     *
     * 用于统计的自定义批号属性 （4)
     *
     * <p lang="en">
     * Batch 4
     *
     * Custom grouping category (4)
     */
    @Term
    public String getBatch4() {
        return getTermLabel("batch4");
    }

    /**
     * 编组5
     *
     * 用于统计的自定义批号属性 （5)
     *
     * <p lang="en">
     * Batch 5
     *
     * Custom grouping category (5)
     */
    @Term
    public String getBatch5() {
        return getTermLabel("batch5");
    }

    /**
     * 编组数目
     *
     * 用于统计的自定义批号属性的个数
     *
     * <p lang="en">
     * Batch Size
     *
     * Custom grouping category attribute count
     */
    @Term
    public String getBatchSize() {
        return getTermLabel("batchSize");
    }

    static SEMInventoryTerms instance = new SEMInventoryTerms();

    public static SEMInventoryTerms getInstance() {
        return instance;
    }

}
