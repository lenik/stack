package com.bee32.plover.restful;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IOperationContext;
import com.bee32.plover.arch.operation.OperationFusion;
import com.bee32.plover.disp.IDispatchContext;

public class Verb
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final int level;

    public Verb(String name) {
        this(name, 1);
    }

    public Verb(String name, int level) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + level;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Verb other = (Verb) obj;
        if (level != other.level)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return <code>null</code> If <code>path</code> doesn't contain this many level of parents.
     */
    public String translate(String path) {
        int lastSlash = lastIndexOf(path, '/', level);
        if (lastSlash == -1)
            return null;

        if (lastSlash == 0)
            return name + "/" + path;

        String prefix = path.substring(0, lastSlash);
        String _suffix = path.substring(lastSlash);

        String translated = prefix + "/" + name + _suffix;
        return translated;
    }

    static int lastIndexOf(String str, char ch, int count) {
        return lastIndexOf(str, ch, str.length(), count);
    }

    static int lastIndexOf(String str, char ch, int from, int count) {
        if (count-- == 0)
            return from;

        int index = str.lastIndexOf(ch, from - 1);
        if (index == -1)
            return count == 0 ? 0 : -1;

        return lastIndexOf(str, ch, index, count);
    }

    public Object operate(IDispatchContext dc, IOperationContext context)
            throws Exception {
        List<String> pathRevList = new ArrayList<String>();
        return operate(dc, context, pathRevList);
    }

    public Object operate(IDispatchContext dc, IOperationContext context, List<String> pathRevList)
            throws Exception {
        Object obj = dc.getObject();

        // XXX - parent = null??
        if (obj != null) {
            OperationFusion fusion = OperationFusion.getInstance();
            IOperation operation = fusion.getOperation(obj, name);

            if (operation != null) {
                String path = pathreverse(pathRevList);
                context.setPath(path);

                Object retval = operation.execute(obj, context);
                context.setReturnValue(retval);

                return obj;
            }
        }

        IDispatchContext parent = dc.getParent();
        if (parent == null)
            return null;

        String[] consumedTokens = dc.getConsumedTokens();
        for (int i = consumedTokens.length - 1; i >= 0; i--)
            pathRevList.add(consumedTokens[i]);

        return operate(dc.getParent(), context, pathRevList);
    }

    static String pathreverse(List<String> stack) {
        int size = stack.size();
        StringBuffer buf = new StringBuffer(size * 30);

        for (int index = size - 1; index >= 0; index--) {
            if (index != 0)
                buf.append('/');
            buf.append(stack.get(index));
        }

        return buf.toString();
    }

}
