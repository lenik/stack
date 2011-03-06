package com.bee32.plover.arch.operation.builtin;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.arch.operation.Operational;
import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.inject.IContextManager;

public class HelloBean
        extends Operational {

    private String lastString;
    private Long lastLong;

    @Operation
    public String hello(String name, int number) {
        String str = name + "/" + number;
        return str;
    }

    public String world(IContextManager context) {
        return "The World";
    }

    public void saveVar(IContainer container)
            throws ContextException {
        this.lastString = container.require(String.class);
        this.lastLong = container.require(Long.class);
    }

    public String getLastString() {
        return lastString;
    }

    public Long getLastLong() {
        return lastLong;
    }

}
