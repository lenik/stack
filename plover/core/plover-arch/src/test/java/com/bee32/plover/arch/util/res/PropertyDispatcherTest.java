package com.bee32.plover.arch.util.res;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Properties;
import java.util.TreeMap;

import org.junit.Test;

public class PropertyDispatcherTest {

    TreeMap<String, String> catExpected;
    TreeMap<String, String> dogExpected;

    public PropertyDispatcherTest() {
        catExpected = new TreeMap<String, String>();
        dogExpected = new TreeMap<String, String>();
        catExpected.put("name", "Tom");
        dogExpected.put("name", "Jerry");
        dogExpected.put("age", "3");
    }

    @Test
    public void testDispatchResourceBundle()
            throws Exception {
        PropertyBuffer catSink = new PropertyBuffer();
        PropertyBuffer dogSink = new PropertyBuffer();

        INlsBundle bundle = NlsBundles.getBundle(getClass().getName());

        PropertyDispatcher dispatcher = new PropertyDispatcher(bundle);
        dispatcher.addPrefixAcceptor("cat.", catSink);
        dispatcher.addPrefixAcceptor("dog", dogSink);

        dispatcher.pull();

        assertEquals(catExpected, catSink.getBufferedMap());
        assertEquals(dogExpected, dogSink.getBufferedMap());
    }

    @Test
    public void testDispatchProperties()
            throws Exception {
        PropertyBuffer catSink = new PropertyBuffer();
        PropertyBuffer dogSink = new PropertyBuffer();

        URL propertiesURL = getClass().getResource(getClass().getSimpleName() + ".properties");
        Properties properties = new Properties();
        properties.load(propertiesURL.openStream());

        PropertyDispatcher dispatcher = new PropertyDispatcher(properties);
        dispatcher.addPrefixAcceptor("cat.", catSink);
        dispatcher.addPrefixAcceptor("dog", dogSink);

        dispatcher.pull();

        assertEquals(catExpected, catSink.getBufferedMap());
        assertEquals(dogExpected, dogSink.getBufferedMap());
    }

}
