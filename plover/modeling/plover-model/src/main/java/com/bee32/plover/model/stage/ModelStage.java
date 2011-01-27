package com.bee32.plover.model.stage;

import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.view.StandardViews;
import com.bee32.plover.model.view.View;

public class ModelStage
        implements IModelStage {

    private static final long serialVersionUID = 1L;

    protected final QualifierMap qualifierMap;

    public ModelStage() {
        qualifierMap = new QualifierMap();
    }

    public ModelStage(View view) {
        this();
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
    }

    @Override
    public void remove(IStagedElement element) {
    }

}
