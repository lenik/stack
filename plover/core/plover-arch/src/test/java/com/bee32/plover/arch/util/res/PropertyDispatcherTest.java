package com.bee32.plover.arch.util.res;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.junit.Test;

import com.bee32.plover.arch.util.res.PropertyBuffer;
import com.bee32.plover.arch.util.res.UniquePrefixDispatcher;

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
        PropertyBuffer catSink = new PropertyBuffer();
        PropertyBuffer dogSink = new PropertyBuffer();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerPrefix("cat.", catSink);
        dispatcher.registerPrefix("dog", dogSink);

        ResourceBundle bundle = ResourceBundle.getBundle(getClass().getName());
        dispatcher.dispatchResourceBundle(bundle);

        assertEquals(catExpected, catSink.getBufferedMap());
        assertEquals(dogExpected, dogSink.getBufferedMap());
    }

    @Test
    public void testVisitProperties()
            throws Exception {
        PropertyBuffer catSink = new PropertyBuffer();
        PropertyBuffer dogSink = new PropertyBuffer();

        UniquePrefixDispatcher dispatcher = new UniquePrefixDispatcher();
        dispatcher.registerPrefix("cat.", catSink);
        dispatcher.registerPrefix("dog", dogSink);

        URL propertiesURL = getClass().getResource(getClass().getSimpleName() + ".properties");
        Properties properties = new Properties();
        properties.load(propertiesURL.openStream());

        dispatcher.dispatchProperties(properties);

        assertEquals(catExpected, catSink.getBufferedMap());
        assertEquals(dogExpected, dogSink.getBufferedMap());
    }

}
