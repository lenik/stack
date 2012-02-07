package com.bee32.plover.faces;

import java.util.Map;

import javax.faces.el.CompositeComponentExpressionHolder;

import com.bee32.plover.faces.el.CompositeComponentExpressionHolderExistence;
import com.bee32.plover.faces.el.CoreConstants;
import com.bee32.plover.faces.el.MapExistence;
import com.bee32.plover.faces.el.StringUtil;
import com.bee32.plover.internet.spam.EmailUtil;
import com.bee32.plover.util.zh.ZhUtil;

/**
 * Suggested ns prefix: "pc".
 */
public class CoreLibrary
        extends _TagLibrary {

    public static String NAMESPACE = "http://bee32.com/plover/core";

    public CoreLibrary() {
        super(NAMESPACE);

        importFunctions(CoreLibrary.class);
        importFunctions(CoreConstants.class);
        importFunctions(StringUtil.class);
        importFunctions(EmailUtil.class);
        importFunctions(ZhUtil.class);
    }

    public static MapExistence exists(Map<String, ?> map) {
        return MapExistence.decorate(map);
    }

    public static CompositeComponentExpressionHolderExistence exprExists(CompositeComponentExpressionHolder exprHolder) {
        return CompositeComponentExpressionHolderExistence.decorate(exprHolder);
    }

}
