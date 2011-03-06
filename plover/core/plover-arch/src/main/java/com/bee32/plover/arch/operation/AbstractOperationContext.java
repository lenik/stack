package com.bee32.plover.arch.operation;

import java.util.TreeMap;

public abstract class AbstractOperationContext
        extends TreeMap<String, Object>
        implements IOperationContext {

    private static final long serialVersionUID = 1L;

    private String path;
    private IResultOutput resultOutput = LoggerResultOutput.getInstance();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public IResultOutput getResultOutput() {
        return resultOutput;
    }

    public void setResultOutput(IResultOutput resultOutput) {
        this.resultOutput = resultOutput;
    }

}
