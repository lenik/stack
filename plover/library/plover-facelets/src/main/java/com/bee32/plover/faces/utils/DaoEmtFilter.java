package com.bee32.plover.faces.utils;

import javax.free.Filt1;

public abstract class DaoEmtFilter
        extends Filt1<String, String> {

    @Override
    public abstract String filter(String message);

}
