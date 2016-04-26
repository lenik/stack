package com.bee32.zebra.tk.repr;

import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.lily.model.base.CoObjectController;
import net.bodz.lily.model.base.CoObjectIndex;

/**
 * To activate this controller, configure path map in the site object:
 * 
 * "table/" -> co-controller.
 */
public class QuickController
        extends CoObjectController {

    public QuickController(IQueryable context, Class<?> objectType, Class<? extends CoObjectIndex> indexClass) {
        super(context, objectType, indexClass);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();
        if (token == null)
            return null;

        QuickIndex index = (QuickIndex) getTarget();
        switch (token) {
        case "index.html":
            break;

        case "plain.html":
            index.format = QuickIndexFormat.PLAIN;
            break;

        case "data.json":
            index.format = QuickIndexFormat.JSON;
            break;

        case "picker.html":
            index.format = QuickIndexFormat.PICKER;
            break;
        }

        if (index != null) {
            index.defaultPage = false;
            return PathArrival.shift(previous, index, tokens);
        }
        return super.dispatch(previous, tokens);
    }

}
