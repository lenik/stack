package com.bee32.sem.term;

import javax.free.IllegalUsageException;
import javax.free.PatternProcessor;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

public class TermMessageInterpolator
        extends PatternProcessor {

    String rootVar = "tr";

    public TermMessageInterpolator() {
        super("$\\{(.*?)\\}");
    }

    @Override
    protected void matched(String part) {
        String expr = matcher.group(1);
        expr = expr.trim();
        if (!expr.startsWith("tr."))
            throw new IllegalUsageException("Unrecognized variable: " + expr);

        expr = expr.substring(3);
        int dot = expr.indexOf('.');
        if (dot == -1)
            throw new IllegalUsageException("No term name in the form [provider].[term]: " + expr);

        String providerName = expr.substring(0, dot);
        String termName = expr.substring(dot + 1);

        ITermProvider provider = TermProviders.getTermProvider(providerName);
        if (provider == null)
            throw new NoSuchTermProviderException(providerName);

        SiteInstance site = ThreadHttpContext.getSiteInstance();
        String termLabel = provider.getTermLabel(site, termName);
        if (termLabel == null)
            throw new NoSuchTermException(termName + " in the term provider " + providerName);

        print(termLabel);
    }

    static TermMessageInterpolator instance;

    public static TermMessageInterpolator getInstance() {
        return instance;
    }

}
