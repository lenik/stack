package com.bee32.plover.arch.util.res;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

public class UniquePrefixDispatcherTest {

    @Ignore("Should use PrefixMap instead.")
    @Test
    public void testCommonPrefix() {
        PropertyBuffer _Sink = new PropertyBuffer();
        PropertyBuffer aSink = new PropertyBuffer();
        PropertyBuffer aaSink = new PropertyBuffer();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerPrefix("", _Sink);
        dispatcher.registerPrefix("a", aSink);
        dispatcher.registerPrefix("aa", aaSink);

        Properties properties = new Properties();
        properties.put("", "*");
        properties.put("a", "*");
        properties.put("b", "*");
        properties.put("aa", "*");
        properties.put("ab", "*");
        properties.put("aaa", "*");
        properties.put("xyz", "*");
        dispatcher.dispatchProperties(properties);

        assertEquals("{=*}", _Sink.getBufferedMap().toString());
        assertEquals("{=*}", aSink.getBufferedMap().toString());
        assertEquals("{=*, a=*}", aaSink.getBufferedMap().toString());
    }

}
