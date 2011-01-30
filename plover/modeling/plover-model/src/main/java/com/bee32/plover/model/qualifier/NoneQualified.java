package com.bee32.plover.model.qualifier;

import java.util.Collections;

public class NoneQualified
        implements IQualified {

    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        return Collections.emptyList();
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        return Collections.emptyList();
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return null;
    }

    private static final NoneQualified instance = new NoneQualified();

    public static NoneQualified getInstance() {
        return instance;
    }

}
