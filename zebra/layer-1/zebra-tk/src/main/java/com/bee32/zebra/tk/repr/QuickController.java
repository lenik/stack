package com.bee32.zebra.tk.repr;

import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.tinylily.model.base.CoObjectController;
import com.tinylily.model.base.CoObjectIndex;

public class QuickController
        extends CoObjectController {

    public QuickController(IQueryable context, Class<?> objectType, Class<? extends CoObjectIndex> indexClass) {
        super(context, objectType, indexClass);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        QuickIndex index = null;
        switch (token) {
        case "index.html":
            index = createIndex();
            break;

        case "plain.html":
            index = createIndex();
            index.format = QuickIndexFormat.PLAIN;
            break;

        case "data.json":
            index = createIndex();
            index.format = QuickIndexFormat.JSON;
            break;

        case "picker.html":
            index = createIndex();
            index.format = QuickIndexFormat.PICKER;
            break;
        }

        if (index != null) {
            index.defaultPage = false;
            return PathArrival.shift(previous, index, tokens);
        }
        return super.dispatch(previous, tokens);
    }

    @Override
    protected QuickIndex createIndex() {
        return (QuickIndex) super.createIndex();
    }

}
