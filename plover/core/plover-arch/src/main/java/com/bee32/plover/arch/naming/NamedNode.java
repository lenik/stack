package com.bee32.plover.arch.naming;

import java.util.Collection;
import java.util.Map;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationBuilder;

public abstract class NamedNode
        extends Component
        implements INamedNode {

    private final INamedNode parent;
    private final Class<?> childType;

    private transient Map<String, IOperation> operationMap;

    /**
     * <ul>
     * <li>base = null, parent != null
     * <li>base != null, parent = null
     * <li>base != null, parent != null: base is ignored.
     * </ul>
     */
    public NamedNode(Class<?> childType, INamedNode parent) {
        if (childType == null && parent == null)
            throw new NullPointerException("Both childType and parent are null");

        this.parent = parent;
        this.childType = childType;

        init();
    }

    public NamedNode(String name, Class<?> childType, INamedNode parent) {
        super(name);

        if (childType == null && parent == null)
            throw new NullPointerException("Both childType and parent are null");

        this.parent = parent;
        this.childType = childType;

        init();
    }

    private void init() {
        if (childType != null) {
            ReverseLookupRegistry registry = ReverseLookupRegistry.getInstance();
            registry.register(this);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public INamedNode getParent() {
        return parent;
    }

    @Override
    public Class<?> getChildType() {
        return childType;
    }

    @Override
    public boolean hasChild(Object obj) {
        return getChildName(obj) != null;
    }

    @Override
    public Collection<IOperation> getOperations() {
        return getOperationMap().values();
    }

    @Override
    public IOperation getOperation(String name) {
        IOperation operation = getOperationMap().get(name);
        return operation;
    }

    public Map<String, IOperation> getOperationMap() {
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
        // builder.discover(getClass());
        // builder.discover(this);
    }

}
