package com.bee32.zebra.tk.site;

import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooIndex;
import com.bee32.zebra.tk.sea.FooIndexFormat;
import com.tinylily.model.base.CoObjectController;
import com.tinylily.model.base.CoObjectIndex;

public class ZooController
        extends CoObjectController {

    public ZooController(IQueryable context, Class<?> objectType, Class<? extends CoObjectIndex> indexClass) {
        super(context, objectType, indexClass);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        FooIndex index = null;
        switch (token) {
        case "index.html":
            index = createIndex();
            break;

        case "data.json":
            index = createIndex();
            index.format = FooIndexFormat.JSON;
            break;

        case "picker.html":
            index = createIndex();
            index.format = FooIndexFormat.PICKER;
            break;
        }

        if (index != null) {
            index.defaultPage = false;
            return PathArrival.shift(previous, index, tokens);
        }
        return super.dispatch(previous, tokens);
    }

    @Override
    protected FooIndex createIndex() {
        return (FooIndex) super.createIndex();
    }

}
