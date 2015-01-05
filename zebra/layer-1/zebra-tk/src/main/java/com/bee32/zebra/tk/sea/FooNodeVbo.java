package com.bee32.zebra.tk.sea;

import com.tinylily.model.base.CoNode;

public class FooNodeVbo<T extends CoNode<T>>
        extends FooVbo<T> {

    public FooNodeVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
