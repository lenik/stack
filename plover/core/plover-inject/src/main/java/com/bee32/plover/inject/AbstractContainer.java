package com.bee32.plover.inject;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

public abstract class AbstractContainer
        extends ContextManager
        implements IContainer {

    public AbstractContainer() {
        super();
    }

    public AbstractContainer(String name) {
        super(name);
    }

    @Override
    public Object getFrameAttribute(Object key) {
        Object frame = getFrame();
        if (frame == null)
            return null;

        if (!(frame instanceof Map<?, ?>))
            throw new IllegalUsageException("Frame is already set to a non-Map object");

        Map<?, ?> mapFrame = (Map<?, ?>) frame;
        return mapFrame.get(key);
    }

    @Override
    public void setFrameAttribute(Object key, Object value) {
        Map<Object, Object> mapFrame = null;

        Object frame = getFrame();

        if (frame instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> _map = (Map<Object, Object>) frame;
            mapFrame = _map;
        }

        else if (frame == null) {
            mapFrame = new HashMap<Object, Object>();
            setFrame(mapFrame);
        }

        else
            throw new IllegalUsageException("Frame is already set to a non-Map object");

        mapFrame.put(key, value);
    }

}
