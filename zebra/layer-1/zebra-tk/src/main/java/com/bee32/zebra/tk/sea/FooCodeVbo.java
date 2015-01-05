package com.bee32.zebra.tk.sea;

import com.tinylily.model.base.CoCode;

public class FooCodeVbo<T extends CoCode<T>>
        extends FooVbo<T> {

    public FooCodeVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
