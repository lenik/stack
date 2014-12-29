package com.bee32.zebra.io.art;

import net.bodz.bas.repr.util.Predef;
import net.bodz.bas.repr.util.PredefMetadata;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocAware;

public class SupplyMethod
        extends Predef<SupplyMethod, Integer> {

    private static final long serialVersionUID = 1L;

    public static final PredefMetadata<SupplyMethod, Integer> METADATA = PredefMetadata.forClass(SupplyMethod.class);

    private SupplyMethod(int val, String name) {
        super(val, name, METADATA);
    }

    /**
     * 采购
     */
    public static final SupplyMethod BUY = new SupplyMethod(1, "BUY");

    /**
     * 生产
     */
    public static final SupplyMethod PRODUCE = new SupplyMethod(2, "PRODUCE");

    static {
        IXjdocAware.fn.injectFields(SupplyMethod.class, false);
    }

}
