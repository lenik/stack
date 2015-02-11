package com.bee32.zebra.erp.fab;

import java.util.ArrayList;
import java.util.List;

import com.tinylily.model.base.CoCode;

/**
 * 操作代码
 */
public class FabOpCode
        extends CoCode<FabOpCode> {

    private static final long serialVersionUID = 1L;

    List<FabQcDef> qcSpec = new ArrayList<FabQcDef>();

}
