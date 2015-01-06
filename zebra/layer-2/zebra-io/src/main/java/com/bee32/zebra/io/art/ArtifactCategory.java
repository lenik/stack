package com.bee32.zebra.io.art;

import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.tinylily.model.base.CoNode;
import com.tinylily.model.base.IdType;

@IdType(Integer.class)
public class ArtifactCategory
        extends CoNode<ArtifactCategory, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int N_SKU_CODE_FORMAT = 100;
    public static final int N_BAR_CODE_FORMAT = 100;

    private String skuCodeFormat;
    private String barCodeFormat;
    private int count;

    /**
     * @label SKU代码格式
     */
    @OfGroup(StdGroup.Preferences.class)
    public String getSkuCodeFormat() {
        return skuCodeFormat;
    }

    public void setSkuCodeFormat(String skuCodeFormat) {
        this.skuCodeFormat = skuCodeFormat;
    }

    /**
     * @label 条形码格式
     */
    @OfGroup(StdGroup.Preferences.class)
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
