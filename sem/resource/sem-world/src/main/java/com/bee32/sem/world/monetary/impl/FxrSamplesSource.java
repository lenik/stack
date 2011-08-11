package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.free.IllegalUsageException;
import javax.free.PrefetchedIterator;
import javax.free.URLResource;

import com.bee32.sem.world.monetary.FxrTable;

public class FxrSamplesSource
        extends BocFxrSource
        implements Iterable<FxrTable> {

    static final String[] FILES = {
            //
            "boc-110722.html", //
            "boc-110723.html", //
            "boc-110803.html", };

    int index = 0;

    public int getCount() {
        return FILES.length;
    }

    @Override
    public int getPreferredInterval() {
        return 1;
    }

    @Override
    public boolean isFinite() {
        return true;
    }

    static class Iter
            extends PrefetchedIterator<FxrTable> {

        FxrSamplesSource src = new FxrSamplesSource();

        @Override
        protected FxrTable fetch() {
            FxrTable table;
            try {
                table = src.download();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            if (table == null)
                return end();
            else
                return table;
        }

    }

    @Override
    public Iterator<FxrTable> iterator() {
        return new Iter();
    }

    protected String httpGet(String uri)
            throws IOException {

        if (index >= FILES.length)
            return null;

        String file = FILES[index++];

        URL url = FxrSamplesSource.class.getResource(file);

        if (url == null)
            throw new IllegalUsageException("Sample file isn't existed: " + file);

        String html = new URLResource(url).forRead().readTextContents();
        return html;
    }

}