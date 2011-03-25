package com.bee32.plover.disp.tree;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IDispatchContext;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;

public class OidDispatcher
        extends AbstractDispatcher {

    @Override
    public int getOrder() {
        return DispatchConfig.ORDER_OID;
    }

    @Override
    public IDispatchContext dispatch(IDispatchContext context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getObject();

        if (!(obj instanceof OidTree<?>))
            return null;

        OidTree<?> tree = (OidTree<?>) obj;

        int index = 0;
        Object lastNode = null;
        int lastNodeIndex = 0;

        while (index < tokens.available()) {
            String token = tokens.peek(index);
            if (!OidUtil.isNumber(token))
                break;

            int ord = Integer.parseInt(token);
            if (!tree.contains(ord))
                break;

            index++;

            tree = tree.get(ord);
            Object node = tree.get();
            if (node != null) {
                lastNode = node;
                lastNodeIndex = index;
            }
        }

        if (lastNode == null)
            return null;

        String[] consumedTokens = tokens.shift(lastNodeIndex);
        return new DispatchContext(context, lastNode, consumedTokens);
    }

}
