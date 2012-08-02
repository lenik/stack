package com.bee32.plover.arch.util.res;

import java.util.MissingResourceException;
import java.util.Set;

public class NlsBundleProperties
        extends AbstractProperties {

    private INlsBundle nlsBundle;

    public NlsBundleProperties(INlsBundle nlsBundle) {
        if (nlsBundle == null)
            throw new NullPointerException("nlsBundle");
        this.nlsBundle = nlsBundle;
    }

    @Override
    public String get(String key) {
        try {
            return nlsBundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    @Override
    public Set<String> keySet() {
        return nlsBundle.keySet();
    }

}
