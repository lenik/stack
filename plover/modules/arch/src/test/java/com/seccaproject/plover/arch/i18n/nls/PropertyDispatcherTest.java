package com.seccaproject.plover.arch.i18n.nls;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
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
    public void testVisitResourceBundle()
            throws Exception {
        BufferPropertySink catSink = new BufferPropertySink();
        BufferPropertySink dogSink = new BufferPropertySink();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerSink("cat.", catSink);
        dispatcher.registerSink("dog", dogSink);

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        dispatcher.visit(bundle);

        assertEquals(catExpected, catSink.getMap());
        assertEquals(dogExpected, dogSink.getMap());
    }

    @Test
    public void testVisitProperties()
            throws Exception {
        BufferPropertySink catSink = new BufferPropertySink();
        BufferPropertySink dogSink = new BufferPropertySink();

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
