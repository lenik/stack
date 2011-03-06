package com.bee32.plover.arch.operation;

import java.util.Map;

import com.bee32.plover.inject.IContainer;

public interface IOperationContext
        extends IContainer, Map<String, Object> {

    String getPath();

    Object get(int index);

    IResultOutput getResultOutput();

    // Logger getLogger();

}
