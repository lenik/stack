package com.bee32.zebra.tk.htm;

import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.lily.model.sea.AbstractTextParametric;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public abstract class GenericPage
        extends AbstractTextParametric
        implements IZebraSiteAnchors {

    @Override
    public String toString() {
        IType type = PotatoTypes.getInstance().forClass(getClass());
        if (type == null)
            throw new NullPointerException("type");

        iString label = type.getLabel();
        if (label == null)
            return "(No label)";
        else
            return label.toString();
    }

}
