package com.bee32.plover.arch.i18n.nls;

import java.util.TreeMap;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.i18n.nls.UniquePrefixDispatcherTest;

/**
 * 唯一前缀分发器。
 * <p>
 * <font color='red'>公共前缀限制：若存在前缀 a, b，且b=a.ω*，则要求不存在任何的c >= a && c <= b</font>
 *
 * @see UniquePrefixDispatcherTest#testCommonPrefix()
 *
 * @test {@link UniquePrefixDispatcherTest}
 */
public class UniquePrefixDispatcher
        extends PrefixDispatcher {

    private TreeMap<String, IPropertySink> registry;

    public UniquePrefixDispatcher() {
        this.registry = new TreeMap<String, IPropertySink>();
    }

    @Override
    public void registerSink(String prefix, IPropertySink sink) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        if (sink == null)
            throw new NullPointerException("sink");
        if (registry.containsKey(prefix))
            throw new IllegalUsageException("Prefix " + prefix + " is already registered by " + registry.get(prefix));
        registry.put(prefix, sink);
    }

    @Override
    protected void dispatch(String key, String content) {
        String prefix = registry.floorKey(key);
        if (prefix == null) {
            // not registered? skip it.
            return;
        }
        if (key.startsWith(prefix)) {
            IPropertySink sink = registry.get(prefix);
            if (sink != null) {
                String suffix = key.substring(prefix.length());
                sink.receive(suffix, content);
            }
        }
    }

}
