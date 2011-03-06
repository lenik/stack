package com.bee32.plover.arch.naming;

import java.io.Serializable;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IParameterMap;
import com.bee32.plover.arch.operation.OperationBuilder;

public class TreeMapNode<T>
        extends NamedNode
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, T> map = new TreeMap<String, T>();
    private final Map<T, String> reverseMap = new IdentityHashMap<T, String>();

    public TreeMapNode(Class<?> baseType) {
        super(baseType, null);
    }

    public TreeMapNode(INamedNode parent) {
        super(null, parent);
    }

    public TreeMapNode(Class<?> baseType, INamedNode parent) {
        super(baseType, parent);
    }

    @Override
    public boolean hasChild(Object obj) {
        return reverseMap.containsKey(obj);
    }

    @Override
    public Object getChild(String locationToken) {
        return map.get(locationToken);
    }

    @Override
    public String getChildName(Object obj) {
        return reverseMap.get(obj);
    }

    public void addChild(String locationToken, T obj) {
        map.put(locationToken, obj);
        reverseMap.put(obj, locationToken);
    }

    public void removeChild(Object obj) {
        String locationToken = reverseMap.remove(obj);
        map.remove(locationToken);
    }

    @Override
    public Collection<String> getChildNames() {
        return map.keySet();
    }

    @Override
    protected void buildOperation(OperationBuilder builder) {
        builder.add(deleteOperation);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String locationToken = entry.getKey();
            T obj = entry.getValue();

            buf.append(locationToken);
            buf.append(" => ");
            buf.append(String.valueOf(obj));
            buf.append('\n');
        }
        return buf.toString();
    }

    static class DeleteOperation
            extends AbstractOperation {

        @Override
        public Object execute(Object instance, IParameterMap parameters)
                throws Exception {
            TreeMapNode<?> node = (TreeMapNode<?>) instance;
            Object key = parameters.get(0);
            Object value = node.map.remove(key);
            if (value != null)
                node.reverseMap.remove(value);
            return null;
        }

    }

    static DeleteOperation deleteOperation = new DeleteOperation();

    public static void main(String[] args) {
        System.out.println(deleteOperation.getName());
    }
}
