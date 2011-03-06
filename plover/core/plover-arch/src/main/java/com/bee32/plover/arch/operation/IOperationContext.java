package com.bee32.plover.arch.operation;

import java.util.Map;

import com.bee32.plover.inject.IContainer;

/**
 * The operation context is designed to be reusable.
 */
public interface IOperationContext
        extends IContainer, Map<String, Object> {

    String getPath();

    void setPath(String path);

    IResultOutput getResultOutput();

    void setResultOutput(IResultOutput resultOutput);

    Object get(int index);

    // Logger getLogger();

    Object getReturnValue();

    void setReturnValue(Object returnValue);

    <T> T getScalar(String key);

    <T> T[] getArray(String key);

}
