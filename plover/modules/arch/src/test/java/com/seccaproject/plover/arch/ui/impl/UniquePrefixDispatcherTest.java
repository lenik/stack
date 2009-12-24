package com.seccaproject.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.junit.Test;

public class UniquePrefixDispatcherTest {

    static class BufferSink
            implements IPrefixSink {

        Map<String, String> bufferMap;

        public BufferSink() {
            this.bufferMap = new TreeMap<String, String>();
        }

        public BufferSink(Map<String, String> buffer) {
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

    TreeMap<String, String> catExpected;
    TreeMap<String, String> dogExpected;

    public UniquePrefixDispatcherTest() {
        catExpected = new TreeMap<String, String>();
        dogExpected = new TreeMap<String, String>();
        catExpected.put("name", "Tom");
        dogExpected.put("name", "Jerry");
        dogExpected.put("age", "3");
    }

    @Test
    public void testProcessResourceBundle()
            throws Exception {
        BufferSink catSink = new BufferSink();
        BufferSink dogSink = new BufferSink();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerSink("cat.", catSink);
        dispatcher.registerSink("dog", dogSink);

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        dispatcher.visit(bundle);

        assertEquals(catExpected, catSink.getMap());
        assertEquals(dogExpected, dogSink.getMap());
    }

    @Test
    public void testProcessProperties()
            throws Exception {
        BufferSink catSink = new BufferSink();
        BufferSink dogSink = new BufferSink();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerSink("cat.", catSink);
        dispatcher.registerSink("dog", dogSink);

        URL propertiesURL = getClass().getResource(getClass().getSimpleName() + ".properties");
        Properties properties = new Properties();
        properties.load(propertiesURL.openStream());

        dispatcher.visit(properties);

        assertEquals(catExpected, catSink.getMap());
        assertEquals(dogExpected, dogSink.getMap());
    }

}
