package com.bee32.plover.arch.util.res;

import java.util.Map;
import java.util.TreeMap;

public class PropertyBuffer
        implements IPropertyAcceptor {

    private Map<String, String> bufferMap;

    public PropertyBuffer() {
        this.bufferMap = new TreeMap<String, String>();
    }

    public PropertyBuffer(Map<String, String> buffer) {
        if (buffer == null)
            throw new NullPointerException("buffer");
        this.bufferMap = buffer;
    }

    @Override
    public void receive(String suffix, String content) {
        bufferMap.put(suffix, content);
    }

    public Map<String, String> getBufferedMap() {
        return bufferMap;
    }

}
