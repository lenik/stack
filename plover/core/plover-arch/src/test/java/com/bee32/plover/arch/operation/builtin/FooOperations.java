package com.bee32.plover.arch.operation.builtin;

import com.bee32.plover.arch.operation.Operation;

public class FooOperations {

    @Operation
    public String hello(String name, int number) {
        String str = name + "/" + number;
        return str;
    }

}
