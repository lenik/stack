package com.bee32.plover.arch.ui.res;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.ui.AbstractImageMap;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.util.res.IPropertyAcceptor;

public class InjectedImageMap
        extends AbstractImageMap
        implements IImageMap, IPropertyAcceptor {

    private final URL contextUrl;
    private final TreeMap<ImageVariant, URL> variantMap;

    public InjectedImageMap(URL contextUrl) {
        if (contextUrl == null)
            throw new NullPointerException("contextUrl");
        this.contextUrl = contextUrl;
        this.variantMap = new TreeMap<ImageVariant, URL>();
    }

    @Override
    public TreeMap<ImageVariant, URL> getVariantMap() {
        return variantMap;
    }

    boolean isSizeHint(String s) {
        return true;
    }

    @Override
    public void receive(String name, String location) {
        assert name != null;
        assert location != null;
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

        ImageVariant variant = new ImageVariant(variantName, widthHint, heightHint);
        URL url;
        if (location.contains("//"))
            try {
                url = new URL(location);
            } catch (MalformedURLException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }
        else {
            try {
                url = new URL(contextUrl, location);
            } catch (MalformedURLException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }
        }
        variantMap.put(variant, url);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(variantMap.size() * 20);
        for (ImageVariant variant : variantMap.keySet()) {
            buf.append(variant);
            buf.append("\n");
        }
        return buf.toString();
    }

}
