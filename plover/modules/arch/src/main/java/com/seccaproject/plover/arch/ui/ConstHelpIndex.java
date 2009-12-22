package com.seccaproject.plover.arch.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import net.bodz.bas.api.exceptions.IllegalUsageException;

/**
 * @test {@link ConstHelpIndexTest}
 */
public class ConstHelpIndex
        implements IHelpIndex {

    private String[] tags;

    // use PrefixMap in future.
    private TreeMap<String, IHelpEntry> tagmap;

    public ConstHelpIndex(IHelpIndex helpIndex) {
        if (helpIndex == null)
            throw new NullPointerException("helpIndex");
        tags = helpIndex.getTags();
        tagmap = new TreeMap<String, IHelpEntry>();

        for (String tag : tags) {
            String prefix = tag + ".";
            Collection<? extends IHelpEntry> entries = helpIndex.getEntries(tag);
            int i = 0;
            for (IHelpEntry entry : entries) {
                tagmap.put(prefix + i, entry);
                i++;
            }
        }
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public Collection<? extends IHelpEntry> getEntries(String tag) {
        tag += ".";
        String tagStart = tagmap.floorKey(tag);
        if (tagStart == null || !tagStart.startsWith(tag))
            return Collections.emptyList();

        // String tagEndIncl = tagmap.floorKey(tag + "\uFFFF");
        // assert tagEndIncl != null;
        // int dot = tagEndIncl.lastIndexOf('.');
        // assert dot != -1;
        // int endIndex = Integer.parseInt(tagEndIncl.substring(dot + 1));
        //
        // int count = endIndex + 1;
        // IHelpEntry[] array = new IHelpEntry[count];
        // for (int i = 0; i < count; i++)
        // array[i] = tagmap.get(tag + i);
        // return Arrays.asList(array);

        return tagmap.subMap(tag + ".", tag + ".\uFFFF").values();
    }

    @Override
    public IHelpEntry getPreferredEntry(String tag) {
        String first = tag + ".0";
        return tagmap.get(first);
    }

    /**
     * Example properties:
     *
     * <pre>
     * tutorial = http://www.example.com/tutorial-1.html
     * tutorial.title = Simple Tutorial
     * tutorial.tags = tutorial simple
     *
     * spec.2009 = my/resource/package/spec-2009.xml
     * spec.2009.title = Reference Specification 2009
     * spec.2009.tags = refspec 2009
     * </pre>
     */
    public static ConstHelpIndex parseResource(Class<?> clazz, Locale locale) {
        HelpIndex helpIndex = new HelpIndex();

        String baseName = clazz.getName();
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.endsWith(".tags")) {
                try {
                    String tags = bundle.getString(key);

                    key = key.substring(0, key.length() - 5);
                    String _url = bundle.getString(key);

                    String title = null;
                    if (bundle.containsKey(key + ".title"))
                        title = bundle.getString(key + ".title");

                    URL url;
                    if (_url.contains("//"))
                        url = new URL(_url);
                    else
                        url = clazz.getResource(_url);

                    String[] tagv = tags.split("\\s+");
                    HelpEntry entry = new HelpEntry(title, url, tagv);

                    helpIndex.add(entry);
                } catch (MissingResourceException e) {
                    continue; // skip if either tags or url not specified.
                } catch (MalformedURLException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }
            }
        }

        return new ConstHelpIndex(helpIndex);
    }

}
