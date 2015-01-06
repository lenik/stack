package com.bee32.zebra.tk.sea;

import com.tinylily.model.base.CoNode;

public abstract class FooNodeVbo<Self extends CoNode<Self, ?>>
        extends FooVbo<Self> {

    public FooNodeVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
