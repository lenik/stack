package com.seccaproject.plover.arch.ui.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import net.bodz.bas.api.exceptions.IllegalUsageException;

import com.seccaproject.plover.arch.i18n.nls.IPropertySink;
import com.seccaproject.plover.arch.ui.AbstractImageMap;
import com.seccaproject.plover.arch.ui.IImageMap;

/**
 * @test {@link ImageMapSinkTest}
 */
public class ImageMapSink
        extends AbstractImageMap
        implements IImageMap, IPropertySink {

    private final Class<?> resourceBase;
    private final TreeMap<VariantKey, URL> map;

    public ImageMapSink(Class<?> resourceBase) {
        if (resourceBase == null)
            throw new NullPointerException("resourceBase");
        this.resourceBase = resourceBase;
        this.map = new TreeMap<VariantKey, URL>();
    }

    @Override
    public URL getImage(String variant, int widthHint, int heightHint) {
        VariantKey wantKey = new VariantKey(variant, widthHint, heightHint);
        while (wantKey != null) {
            VariantKey searchKey = map.floorKey(wantKey);
            if (searchKey.implies(wantKey))
                return map.get(searchKey);
            // searchKey = searchKey.getParent();
            wantKey = wantKey.getParent();
        }
        return null;
    }

    boolean isSizeHint(String s) {
        return true;
    }

    @Override
    public void receive(String name, String value) {
        assert name != null;
        assert value != null;
        int widthHint = 0;
        int heightHint = 0;
        String variantName = name;
        String sizeHint = null;
        int dash = name.lastIndexOf('-');
        if (dash != -1) {
            String suffix = name.substring(dash + 1);
            if (isSizeHint(suffix)) {
                sizeHint = suffix;
                variantName = name.substring(0, dash);
            }
        } else if (isSizeHint(name)) {
            sizeHint = name;
            variantName = "";
        }

        if (sizeHint != null)
            try {
                int x = sizeHint.indexOf('x');
                if (x == -1)
                    widthHint = Integer.parseInt(sizeHint);
                else {
                    widthHint = Integer.parseInt(sizeHint.substring(0, x));
                    heightHint = Integer.parseInt(sizeHint.substring(x + 1));
                }
            } catch (NumberFormatException e) {
                widthHint = 0;
                heightHint = 0;
                variantName = name;
            }

        VariantKey key = new VariantKey(variantName, widthHint, heightHint);
        URL url;
        if (value.contains("//"))
            try {
                url = new URL(value);
            } catch (MalformedURLException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }
        else {
            url = resourceBase.getResource(value);
            if (url == null)
                throw new IllegalUsageException("Class resource isn't existed: " + value);
        }
        map.put(key, url);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer(map.size() * 20);
        for (VariantKey key : map.keySet()) {
            buf.append(key);
            buf.append("\n");
        }
        return buf.toString();
    }

}

class VariantKey
        implements Comparable<VariantKey> {

    private static final int DEFAULT_HINT = 10000;

    private final String variantName;
    private final int widthHint;
    private final int heightHint;

    public VariantKey(String variantName, int widthHint, int heightHint) {
        if (widthHint == 0)
            widthHint = DEFAULT_HINT;
        if (heightHint == 0)
            heightHint = DEFAULT_HINT;
        if (variantName == null)
            throw new NullPointerException("variantName");
        this.variantName = variantName;
        this.widthHint = widthHint;
        this.heightHint = heightHint;
    }

    @Override
    public int compareTo(VariantKey o) {
        int cmp = variantName.compareTo(o.variantName);
        if (cmp != 0)
            return cmp;
        if ((cmp = o.widthHint - widthHint) != 0)
            return cmp;
        if ((cmp = o.heightHint - heightHint) != 0)
            return cmp;
        return 0;
    }

    public boolean implies(VariantKey o) {
        if (o.variantName.startsWith(variantName)) {
            String oSuffix = o.variantName.substring(variantName.length());
            if (!oSuffix.isEmpty()) {
                char joinChar = oSuffix.charAt(0);
                // is punctuation?
                if (Character.isLetterOrDigit(joinChar))
                    return false;
            }
            // assert o.variantName.equals(variantName);
            if (widthHint >= o.widthHint || heightHint >= o.heightHint)
                return true;
        }
        return false;
    }

    public VariantKey getParent() {
        // if (widthHint != 0 || heightHint != 0)
        // return new VariantKey(variantName, 0, 0);
        int minor = variantName.lastIndexOf('-');
        if (minor == -1)
            minor = variantName.lastIndexOf('.');
        if (minor != -1) {
            String minorName = variantName.substring(0, minor);
            return new VariantKey(minorName, widthHint, heightHint);
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = variantName.hashCode();
        hash ^= widthHint * 0x833bfde9;
        hash ^= heightHint * 0x64f08afb;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VariantKey))
            return false;
        VariantKey o = (VariantKey) obj;
        return variantName.equals(o.variantName) //
                && widthHint == o.widthHint //
                && heightHint == o.heightHint;
    }

    @Override
    public String toString() {
        return variantName + "-" + widthHint + "x" + heightHint;
    }

}
