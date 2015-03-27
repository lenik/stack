package com.bee32.zebra.tk.htm;

import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.tinylily.model.sea.AbstractTextParametric;

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
            throw new NullPointerException("label");
        return label.toString();
    }

}
