package com.bee32.plover.arch.util.res;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.free.PrefetchedIterator;
import javax.free.ReadOnlyException;

public abstract class AbstractProperties
        implements IProperties {

    protected class Iter
            extends PrefetchedIterator<Entry<String, String>>
            implements Entry<String, String> {

        private String key;
        private Iterator<String> keyIter = keySet().iterator();

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return get(key);
        }

        @Override
        public String setValue(String value) {
            throw new ReadOnlyException();
        }

        @Override
        protected Entry<String, String> fetch() {
            if (!keyIter.hasNext())
                return end();
            this.key = keyIter.next();
            return this;
        }

    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        return new Iter();
    }

}
