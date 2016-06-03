package com.bee32.zebra.tk.repr;

import java.lang.reflect.Constructor;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.repr.path.INoPathRef;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.AbstractQueryable;
import net.bodz.lily.model.base.CoObjectIndex;

/**
 * To activate this controller, configure path map in the site object:
 * 
 * "table/" -> co-controller.
 */
public class QuickController
        extends AbstractQueryable
        implements IPathDispatchable, INoPathRef {

    private DataContext dataContext;
    private Class<?> objectType;

    private Constructor<? extends CoObjectIndex<?>> indexCtor;
    private CoObjectIndex<?> index;

    public QuickController(DataContext dataContext, Class<?> objectType, Class<? extends CoObjectIndex<?>> indexClass) {
        if (dataContext == null)
            throw new NullPointerException("dataContext");
        if (objectType == null)
            throw new NullPointerException("entityType");
        this.dataContext = dataContext;
        this.objectType = objectType;
        try {
            this.indexCtor = indexClass.getConstructor(DataContext.class);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Can't get constructor from " + indexClass, e);
        }
    }

    public Class<?> getObjectType() {
        return objectType;
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();
        if (token == null)
            return null;

        QuickIndex<?> index = (QuickIndex<?>) getTarget();
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
        return null;
    }

    @Override
    public synchronized CoObjectIndex<?> getTarget() {
        if (index == null)
            try {
                index = indexCtor.newInstance(dataContext);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to create index instance: " + e.getMessage(), e);
            }
        return index;
    }

}
