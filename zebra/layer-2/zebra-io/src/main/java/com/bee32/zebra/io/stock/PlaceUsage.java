package com.bee32.zebra.io.stock;

import net.bodz.bas.t.predef.Predef;
import net.bodz.bas.t.predef.PredefMetadata;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocAware;

/**
 * 库位用途
 */
public class PlaceUsage
        extends Predef<PlaceUsage, Integer> {

    private static final long serialVersionUID = 1L;

    public static final PredefMetadata<PlaceUsage, Integer> METADATA = PredefMetadata.forClass(PlaceUsage.class);

    private PlaceUsage(int val, String name) {
        super(val, name, METADATA);
    }

    /**
     * 虚拟组
     */
    public static final PlaceUsage GROUP = new PlaceUsage(1, "GROUP");

    /**
     * 企业
     */
    public static final PlaceUsage INTERNAL = new PlaceUsage(2, "INTERNAL");

    /**
     * 供应商
     */
    public static final PlaceUsage SUPPLIER = new PlaceUsage(3, "SUPPLIER");

    /**
     * 客户
     */
    public static final PlaceUsage CUSTOMER = new PlaceUsage(4, "CUSTOMER");

    /**
     * 残次品
     */
    public static final PlaceUsage SUBQUALITY = new PlaceUsage(5, "SUBQUALITY");

    /**
     * 废品
     */
    public static final PlaceUsage WASTE = new PlaceUsage(6, "WASTE");

    static {
        IXjdocAware.fn.injectFields(PlaceUsage.class, false);
    }

}
