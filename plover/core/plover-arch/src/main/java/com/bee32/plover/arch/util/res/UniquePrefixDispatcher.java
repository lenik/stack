package com.bee32.plover.arch.util.res;

import java.util.TreeMap;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 唯一前缀分发器。
 * <p>
 * <font color='red'>公共前缀限制：若存在前缀 a, b，且b=a.ω*，则要求不存在任何的c >= a && c <= b</font>
 *
 * @see UniquePrefixDispatcherTest#testCommonPrefix()
 */
public class UniquePrefixDispatcher
        extends PrefixDispatcher {

    private static Logger logger = LoggerFactory.getLogger(UniquePrefixDispatcher.class);

    private TreeMap<String, IPropertyAcceptor> uniquePrefixMap;

    public UniquePrefixDispatcher() {
        this.uniquePrefixMap = new TreeMap<String, IPropertyAcceptor>();
    }

    @Override
    public void registerPrefix(String prefix, IPropertyAcceptor sink) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        if (sink == null)
            throw new NullPointerException("sink");

        if (uniquePrefixMap.containsKey(prefix)) {
            IPropertyAcceptor _sink = uniquePrefixMap.get(prefix);
            throw new IllegalUsageException("Prefix " + prefix + " is already registered with " + _sink);
        }

        uniquePrefixMap.put(prefix, sink);
    }

    @Override
    protected void dispatch(String key, String content) {
        String prefix = uniquePrefixMap.floorKey(key);
        if (prefix == null) {
            logger.debug("Skipped property: " + key, prefix);
            return;
        }

        if (key.startsWith(prefix)) {
            IPropertyAcceptor sink = uniquePrefixMap.get(prefix);
            if (sink != null) {
                String suffix = key.substring(prefix.length());
                sink.receive(suffix, content);
            }
        }
    }

}
