package com.bee32.zebra.erp.stock;

import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.CoNode;

public class ArtifactCategory
        extends CoNode<ArtifactCategory> {

    private static final long serialVersionUID = 1L;

    public static final int N_SKU_CODE_FORMAT = 100;
    public static final int N_BAR_CODE_FORMAT = 100;

    private int id;
    private String skuCodeFormat;
    private String barCodeFormat;
    private int count;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @label SKU代码格式
     */
    @OfGroup(OaGroups.Identity.class)
    public String getSkuCodeFormat() {
        return skuCodeFormat;
    }

    public void setSkuCodeFormat(String skuCodeFormat) {
        this.skuCodeFormat = skuCodeFormat;
    }

    /**
     * @label 条形码格式
     */
    @OfGroup(OaGroups.Identity.class)
    public String getBarCodeFormat() {
        return barCodeFormat;
    }

    public void setBarCodeFormat(String barCodeFormat) {
        this.barCodeFormat = barCodeFormat;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
