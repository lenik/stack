package com.seccaproject.plover.arch.i18n.nls;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

public class UniquePrefixDispatcherTest {

    @Ignore("Should use PrefixMap instead.")
    @Test
    public void testCommonPrefix() {
        BufferPropertySink _Sink = new BufferPropertySink();
        BufferPropertySink aSink = new BufferPropertySink();
        BufferPropertySink aaSink = new BufferPropertySink();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerSink("", _Sink);
        dispatcher.registerSink("a", aSink);
        dispatcher.registerSink("aa", aaSink);

        Properties properties = new Properties();
        properties.put("", "*");
        properties.put("a", "*");
        properties.put("b", "*");
        properties.put("aa", "*");
        properties.put("ab", "*");
        properties.put("aaa", "*");
        properties.put("xyz", "*");
        dispatcher.visit(properties);

        assertEquals("{=*}", _Sink.getMap().toString());
        assertEquals("{=*}", aSink.getMap().toString());
        assertEquals("{=*, a=*}", aaSink.getMap().toString());
    }

}
