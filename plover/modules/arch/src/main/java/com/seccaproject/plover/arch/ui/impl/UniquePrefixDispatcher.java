package com.seccaproject.plover.arch.ui.impl;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.bodz.bas.api.exceptions.IllegalUsageException;

/**
 * 唯一前缀分发器
 *
 * @test {@link UniquePrefixDispatcherTest}
 */
public class UniquePrefixDispatcher {

    private TreeMap<String, IPrefixSink> registry;

    public UniquePrefixDispatcher() {
        this.registry = new TreeMap<String, IPrefixSink>();
    }

    public void registerSink(String prefix, IPrefixSink sink) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        if (sink == null)
            throw new NullPointerException("sink");
        if (registry.containsKey(prefix))
            throw new IllegalUsageException("Prefix " + prefix + " is already registered by " + registry.get(prefix));
        registry.put(prefix, sink);
    }

    public void visit(ResourceBundle bundle) {
        if (bundle == null)
            throw new NullPointerException("bundle");
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String prefix = registry.floorKey(key);
            if (key.startsWith(prefix)) {
                IPrefixSink sink = registry.get(prefix);
                if (sink != null) {
                    String suffix = key.substring(prefix.length());
                    String content = bundle.getString(key);
                    // assert no MRE exception.
                    sink.receive(suffix, content);
                }
            }
        }
    }

    public void visit(Properties properties) {
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String prefix = registry.floorKey(key);
            if (key.startsWith(prefix)) {
                IPrefixSink sink = registry.get(prefix);
                if (sink != null) {
                    String suffix = key.substring(prefix.length());
                    String content = properties.getProperty(key);
                    sink.receive(suffix, content);
                }
            }
        }
    }

}
