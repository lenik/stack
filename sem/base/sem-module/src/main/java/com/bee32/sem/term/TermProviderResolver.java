package com.bee32.sem.term;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component("tr")
public class TermProviderResolver
        extends AbstractMap<String, ITermProvider> {

    // TermMessageInterpolator interpolator = TermMessageInterpolator.getInstance();

    @Override
    public ITermProvider get(Object key) {
        String providerName = (String) key;
        ITermProvider provider = TermProviders.getTermProvider(providerName);
        return provider;
    }

    @Override
    public Set<Map.Entry<String, ITermProvider>> entrySet() {
        return Collections.emptySet();
    }

}
