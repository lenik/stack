package com.bee32.plover.model.qualifier;

import java.util.Iterator;

import javax.free.ConcatIterator;

public class MergeQualified
        implements IQualified {

    private final IQualified a;
    private final IQualified b;

    MergeQualified(IQualified a, IQualified b) {
        if (a == null)
            throw new NullPointerException("a");
        if (b == null)
            throw new NullPointerException("b");
        this.a = a;
        this.b = b;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        Iterable aq = a.getQualifiers();
        Iterable bq = b.getQualifiers();
        return concat(aq, bq);
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        Iterable<Q> aq = a.getQualifiers(qualifierType);
        Iterable<Q> bq = b.getQualifiers(qualifierType);
        return concat(aq, bq);
    }

    static <Q extends Qualifier<Q>> Iterable<Q> concat(final Iterable<Q> aq, final Iterable<Q> bq) {
        return new Iterable<Q>() {

            @Override
            public Iterator<Q> iterator() {
                Iterator<Q> ai = aq.iterator();
                Iterator<Q> bi = bq.iterator();
                ConcatIterator<Q> cit = new ConcatIterator<Q>(ai, bi);
                return cit;
            }

        };
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        Q q = a.getQualifier(qualifierType);
        if (q != null)
            return q;
        q = b.getQualifier(qualifierType);
        return q;
    }

    public static IQualified merge(IQualified a, IQualified b) {
        if (a == null)
            if (b == null)
                return NoneQualified.getInstance();
            else
                return b;
        else if (b == null)
            return a;
        else
            return new MergeQualified(a, b);
    }

}
