package com.bee32.plover.arch.i18n.nls;

import java.util.Map;
import java.util.TreeMap;

public class BufferPropertySink
        implements IPropertySink {

    private Map<String, String> bufferMap;

    public BufferPropertySink() {
        this.bufferMap = new TreeMap<String, String>();
    }

    public BufferPropertySink(Map<String, String> buffer) {
        if (buffer == null)
            throw new NullPointerException("buffer");
        this.bufferMap = buffer;
    }

    @Override
    public void receive(String suffix, String content) {
        bufferMap.put(suffix, content);
    }

    public Map<String, String> getMap() {
        return bufferMap;
    }

}
