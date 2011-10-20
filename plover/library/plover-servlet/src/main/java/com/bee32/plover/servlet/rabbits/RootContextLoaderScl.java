package com.bee32.plover.servlet.rabbits;

import org.springframework.web.context.ContextLoaderListener;

import com.bee32.plover.servlet.peripheral.DecoratedScl;

public class RootContextLoaderScl
        extends DecoratedScl {

    @Override
    public int getPriority() {
        return -10;
    }

    public RootContextLoaderScl() {
        super(ContextLoaderListener.class);
    }

}
