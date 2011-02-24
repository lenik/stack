package com.bee32.plover.model.qualifier;

import com.bee32.plover.arch.Component;

public class Qualified
        extends Component
        implements IQualified {

    protected final QualifierMap qualifierMap = new QualifierMap();

    public Qualified() {
        super();
    }

    public Qualified(String name) {
        super(name);
    }

    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        return qualifierMap.getQualifiers();
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        return qualifierMap.getQualifiers(qualifierType);
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return qualifierMap.getQualifier(qualifierType);
    }

}
