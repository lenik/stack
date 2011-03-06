package com.bee32.plover.arch.operation;

import java.util.Collection;
import java.util.Map;

public abstract class Operational
        implements IOperational {

    private transient Map<String, IOperation> operationMap;

    @Override
    public Collection<IOperation> getOperations() {
        return getOperationMap().values();
    }

    @Override
    public IOperation getOperation(String name) {
        IOperation operation = getOperationMap().get(name);
        return operation;
    }

    final Map<String, IOperation> getOperationMap() {
        if (operationMap == null) {
            synchronized (this) {
                if (operationMap == null) {
                    OperationBuilder operationBuilder = new OperationBuilder();
                    buildOperation(operationBuilder);
                    operationMap = operationBuilder.getMap();
                }
            }
        }
        return operationMap;
    }

    protected void buildOperation(OperationBuilder builder) {
        builder.discover(getClass());
        builder.discover(this);
    }

}
