package com.bee32.sem.frame;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.component.UIComponent;

import com.bee32.plover.faces._TagLibrary;
import com.bee32.sem.frame.ui.ELSelector;

public class FrameLibrary
        extends _TagLibrary {

    public static final String NAMESPACE = "http://java.sun.com/jsf/composite/frame";

    public FrameLibrary() {
        super(NAMESPACE);

        importFunctions(FrameLibrary.class);
    }

    public static ELSelector selector(UIComponent component, List<?> items, String valueExpr, String varName,
            String itemKeyExpr, String itemLabelExpr, String nullId) {

        valueExpr = "${" + valueExpr + "}";

        if (itemKeyExpr != null) {
            if (itemKeyExpr.isEmpty())
                itemKeyExpr = null;
        } else
            itemKeyExpr = "${" + itemKeyExpr + "}";

        if (itemLabelExpr != null) {
            if (itemLabelExpr.isEmpty())
                itemLabelExpr = null;
        } else
            itemLabelExpr = "${" + itemLabelExpr + "}";

        ELSelector selector = new ELSelector(items, valueExpr, varName, itemKeyExpr, itemLabelExpr) {

            @Override
            protected ELContext getELContext() {
                return super.getELContext();
            }

            @Override
            protected ExpressionFactory getExpressionFactory() {
                return super.getExpressionFactory();
            }

        };

        if (nullId != null)
            selector.setNullId(nullId);
        return selector;
    }

}
