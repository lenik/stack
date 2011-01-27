package com.bee32.plover.model.view;

import com.bee32.plover.model.qualifier.DerivedQualifier;

public class View
        extends DerivedQualifier<View> {

    private static final long serialVersionUID = 1L;

    public View(String name) {
        this(name, null);
    }

    public View(String name, View kindOf) {
        super(View.class, name, kindOf);
    }

}
