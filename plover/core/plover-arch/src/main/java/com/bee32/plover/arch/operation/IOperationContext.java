package com.bee32.plover.arch.operation;

import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.inject.IContainer;

/**
 * The operation context is designed to be reusable.
 */
public interface IOperationContext
        extends IContainer, IStruct {

    String getPath();

    void setPath(String path);

    Object get(int index);

    IResultOutput getResultOutput();

    void setResultOutput(IResultOutput resultOutput);

    // Logger getLogger();

    Object getReturnValue();

    void setReturnValue(Object returnValue);

}
