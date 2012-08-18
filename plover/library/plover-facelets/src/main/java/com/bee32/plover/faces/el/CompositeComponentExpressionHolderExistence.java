package com.bee32.plover.faces.el;

import javax.el.ValueExpression;
import javax.faces.el.CompositeComponentExpressionHolder;
import javax.free.ReadOnlyException;

import com.bee32.plover.servlet.rtx.YesMap;

public class CompositeComponentExpressionHolderExistence
        extends YesMap<Boolean> {

    CompositeComponentExpressionHolder orig;

    private CompositeComponentExpressionHolderExistence(CompositeComponentExpressionHolder orig) {
        if (orig == null)
            throw new NullPointerException("orig");
        this.orig = orig;
    }

    @Override
    public boolean containsValue(Object value) {
        return value instanceof Boolean;
    }

    @Override
    public Boolean get(Object key) {
        String name = String.valueOf(key);
        ValueExpression ve = orig.getExpression(name);
        return ve != null;
    }

    @Override
    public Boolean put(String key, Boolean value) {
        throw new ReadOnlyException();
    }

    public static CompositeComponentExpressionHolderExistence decorate(CompositeComponentExpressionHolder holder) {
        return new CompositeComponentExpressionHolderExistence(holder);
    }

}
