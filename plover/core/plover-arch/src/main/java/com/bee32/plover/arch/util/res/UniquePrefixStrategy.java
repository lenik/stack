package com.bee32.plover.arch.util.res;

import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.PrefixMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 唯一前缀分发器。
 * <p>
 * <font color='red'>公共前缀限制：若存在前缀 a, b，且b=a.ω*，则要求不存在任何的c >= a && c <= b</font>
 *
 * @see UniquePrefixDispatcherTest#testCommonPrefix()
 */
public class UniquePrefixStrategy
        extends PrefixDispatchStrategy {

    private static Logger logger = LoggerFactory.getLogger(UniquePrefixStrategy.class);

    private PrefixMap<IPropertyAcceptor> prefixMap;

    public UniquePrefixStrategy() {
        this.prefixMap = new PrefixMap<IPropertyAcceptor>();
    }

    @Override
    public void registerPrefix(String prefix, IPropertyAcceptor sink) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        if (sink == null)
            throw new NullPointerException("sink");

        if (prefixMap.containsKey(prefix)) {
            IPropertyAcceptor _sink = prefixMap.get(prefix);
            throw new IllegalUsageException("Prefix " + prefix + " is already registered with " + _sink);
        }

        prefixMap.put(prefix, sink);
    }

    @Override
    public Iterable<IPropertyAcceptor> getAcceptors() {
        return prefixMap.values();
    }

    @Override
    public Map<String, ? extends IPropertyAcceptor> getAcceptorMap() {
        return prefixMap;
    }

    @Override
    public void dispatch(String key, String content) {
        String prefix = prefixMap.floorKey(key);
        if (prefix == null || !key.startsWith(prefix)) {
            logger.debug("Skipped property: " + key);
            return;
        }

        IPropertyAcceptor sink = prefixMap.get(prefix);
        assert sink != null : "Null sink";

        String suffix = key.substring(prefix.length());
        sink.receive(suffix, content);
    }

}
