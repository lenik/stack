package com.bee32.plover.arch.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.res.INlsBundle;
import com.bee32.plover.arch.util.res.NlsBundles;

/**
 * 平面化了的参考信息集。
 * <p>
 * 通过使用 {@link RefdocsBuilder} 来建立中间内存模型，并最后简化为平面模型。
 */
public class PlainRefdocs
        implements IRefdocs {

    private Set<String> tags;

    // use PrefixMap in future.
    private TreeMap<String, IRefdocEntry> qualifiedEntries;

    public PlainRefdocs(IRefdocs refdocs) {
        if (refdocs == null)
            throw new NullPointerException("refdocs");
        tags = refdocs.getTags();
        qualifiedEntries = new TreeMap<String, IRefdocEntry>();

        for (String tag : tags) {
            String prefix = tag + ".";
            int i = 0;
            for (IRefdocEntry entry : refdocs.getEntries(tag)) {
                qualifiedEntries.put(prefix + i++, entry);
                i++;
            }
        }
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }

    /**
     * List all matching entries in the optimized way.
     */
    @Override
    public Collection<? extends IRefdocEntry> getEntries(String tag) {
        tag += ".";
        String tagStart = qualifiedEntries.floorKey(tag);
        if (tagStart == null || !tagStart.startsWith(tag))
            return Collections.<IRefdocEntry> emptyList();

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

        return qualifiedEntries.subMap(tag + ".", tag + ".\uFFFF").values();
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        String first = tag + ".0";
        return qualifiedEntries.get(first);
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
    public static PlainRefdocs parseResource(Class<?> clazz, Locale locale) {
        String baseName = clazz.getName();
        INlsBundle bundle = NlsBundles.getBundle(baseName, locale);
        return parseResource(bundle, clazz, locale);
    }

    public static PlainRefdocs parseResource(INlsBundle bundle, Locale locale) {
        return parseResource(bundle, null, locale);
    }

    static PlainRefdocs parseResource(INlsBundle bundle, Class<?> clazz, Locale locale) {
        RefdocsBuilder builder = new RefdocsBuilder();

        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.endsWith(".url")) {
                try {
                    String _url = bundle.getString(key);
                    key = key.substring(0, key.length() - 4);

                    String title = null;
                    String tags = null;

                    if (bundle.containsKey(key + ".title"))
                        title = bundle.getString(key + ".title");

                    if (bundle.containsKey(key + ".tags"))
                        tags = bundle.getString(key + ".tags");
                    if (tags == null)
                        continue;
                    // tags=tags.trim();
                    if (tags.isEmpty())
                        continue;

                    URL url;
                    if (!_url.contains("//") && clazz != null)
                        url = clazz.getResource(_url);
                    else
                        url = new URL(_url);

                    String[] tagv = tags.split("\\s+");
                    RefdocEntry entry = new RefdocEntry(title, url, tagv);

                    builder.add(entry);
                } catch (MissingResourceException e) {
                    continue; // skip if either tags or url not specified.
                } catch (MalformedURLException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }
            }
        }
        return new PlainRefdocs(builder);
    }

}
