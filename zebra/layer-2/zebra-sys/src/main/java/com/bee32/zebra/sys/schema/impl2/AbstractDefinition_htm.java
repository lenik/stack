package com.bee32.zebra.sys.schema.impl2;

import net.bodz.bas.site.IBasicSiteAnchors;

import com.bee32.zebra.tk.slim.SlimCodeForm_htm;
import com.tinylily.model.base.schema.AbstractDefinition;

public abstract class AbstractDefinition_htm<T extends AbstractDefinition<T>>
        extends SlimCodeForm_htm<T>
        implements IBasicSiteAnchors {

    public AbstractDefinition_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
