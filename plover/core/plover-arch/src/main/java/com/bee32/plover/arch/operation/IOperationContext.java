package com.bee32.plover.arch.operation;

import java.util.Map;

public interface IOperationContext
        extends Map<String, Object> {

    String getPath();

    Object get(int index);

    IResultOutput getResultOutput();

    // Logger getLogger();

}
