package com.bee32.plover.arch.util.res;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class UniquePrefixDispatcherTest {

    @Test
    public void testCommonPrefix() {
        PropertyBuffer _Sink = new PropertyBuffer();
        PropertyBuffer aSink = new PropertyBuffer();
        PropertyBuffer aaSink = new PropertyBuffer();

        Properties properties = new Properties();
        properties.put("", "*");
        properties.put("a", "*");
        properties.put("b", "*");
        properties.put("aa", "*");
        properties.put("ab", "*");
        properties.put("aaa", "*");
        properties.put("xyz", "*");

        PropertiesPropertyDispatcher dispatcher = new PropertiesPropertyDispatcher(properties);
        dispatcher.addPrefixAcceptor("", _Sink);
        dispatcher.addPrefixAcceptor("a", aSink);
        dispatcher.addPrefixAcceptor("aa", aaSink);

        dispatcher.require();

        assertEquals("{=*, b=*, xyz=*}", _Sink.getBufferedMap().toString());
        assertEquals("{=*, b=*}", aSink.getBufferedMap().toString());
        assertEquals("{=*, a=*}", aaSink.getBufferedMap().toString());
    }

}
