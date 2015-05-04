package com.bee32.zebra.tk.slim;

import net.bodz.lily.model.base.CoCode;

public class SlimCodeForm_htm<T extends CoCode<T>>
        extends SlimForm_htm<T> {

    public SlimCodeForm_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

}
