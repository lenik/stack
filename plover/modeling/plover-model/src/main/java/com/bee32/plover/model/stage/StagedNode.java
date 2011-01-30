package com.bee32.plover.model.stage;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.bee32.plover.arch.Component;
import com.bee32.plover.model.qualifier.MergeQualified;
import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.schema.ISchema;

public abstract class StagedNode
        extends Component
        implements IStagedElement {

    private transient ISchema schema;
    private QualifierMap qmap;

    private List<IStagedElement> children;

    public StagedNode() {
        super();
    }

    public StagedNode(String name) {
        super(name);
    }

    @Override
    public ISchema getSchema() {
        if (schema == null)
            schema = buildSchema();
        return schema;
    }

    protected ISchema buildSchema() {
        return null;
    }

    @Override
    public Class<?> getType() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object obj) {
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        return MergeQualified.merge(qmap, getSchema()).getQualifiers();
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        return MergeQualified.merge(qmap, getSchema()).getQualifiers(qualifierType);
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return MergeQualified.merge(qmap, getSchema()).getQualifier(qualifierType);
    }

    @Override
    public Iterator<IStagedElement> iterator() {
        if (children == null) {
            List<IStagedElement> emptyList = Collections.emptyList();
            return emptyList.iterator();
        } else
            return children.iterator();
    }

    public void add(IStagedElement element) {
        if (element == null)
            throw new NullPointerException("element");
        children.add(element);
    }

    public void remove(IStagedElement element) {
        children.remove(element);
    }

}
