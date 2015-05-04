package com.bee32.zebra.tk.slim;

import net.bodz.lily.model.base.CoNode;

public abstract class SlimNodeForm_htm<Self extends CoNode<Self, ?>>
        extends SlimForm_htm<Self> {

    public SlimNodeForm_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
