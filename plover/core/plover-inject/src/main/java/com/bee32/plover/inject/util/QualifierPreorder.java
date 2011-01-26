package com.bee32.plover.inject.util;

import javax.free.AbstractPreorder;

public abstract class QualifierPreorder<Q>
        extends AbstractPreorder<Q> {

    @Override
    public abstract int compare(Q a, Q b);

    @Override
    public abstract Q getPreceding(Q qualifier);

    @Override
    public abstract int precompare(Q a, Q b);

}
