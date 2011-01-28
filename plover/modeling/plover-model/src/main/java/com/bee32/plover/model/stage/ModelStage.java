package com.bee32.plover.model.stage;

import com.bee32.plover.inject.DelegatedContainer;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.view.StandardViews;
import com.bee32.plover.model.view.View;

public class ModelStage
        extends DelegatedContainer
        implements IModelStage {

    private static final long serialVersionUID = 1L;

    protected final QualifierMap qualifierMap;

    public ModelStage(IContainer container) {
        super(container);
        qualifierMap = new QualifierMap();
    }

    public ModelStage(IContainer container, View view) {
        this(container);
        qualifierMap.setQualifier(view);
    }

    public View getView() {
        View view = getQualifier(View.class);
        if (view == null)
            view = StandardViews.VIEW;
        return view;
    }

    @Override
    public Iterable<? extends Qualifier<?>> getQualifiers() {
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

    @Override
    public void add(IStagedElement element) {
        element.getElementType();
    }

    @Override
    public void remove(IStagedElement element) {
    }

}
