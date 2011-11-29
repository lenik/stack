package com.bee32.plover.ox1.dict;

import javax.free.StartswithPreorder;

public class PrefixTreeBuilder
        extends PoTreeBuilder<String, String> {

    public PrefixTreeBuilder() {
        super(IdKeyMapper.<String> getInstance(), StartswithPreorder.getInstance());
    }

}
