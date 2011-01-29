package com.bee32.plover.disp.tree;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;

public class OidDispatcher
        extends AbstractDispatcher {

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof OidTree<?>))
            return null;

        OidTree<?> tree = (OidTree<?>) context;

        int index = 0;
        while (index < tokens.available()) {
            String token = tokens.peek(index++);

            if (OidUtil.isNumber(token)) {
                int ord = Integer.parseInt(token);
                if (!tree.contains(ord))
                    return null;

                tree = tree.get(ord);
                Object node = tree.get();
                if (node != null) {
                    tokens.skip(index);
                    return node;
                }
            }
        }
        return null;
    }

}
