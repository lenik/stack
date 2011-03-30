package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.Component;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.IEntity;

@ComponentTemplate
@Lazy
public abstract class EntitySamplesContribution
        extends Component
        implements IEntitySamplesContribution {

    private int order;

    private List<IEntity<?>> normalSamples;
    private List<IEntity<?>> worseSamples;

    private boolean assembled;

    public EntitySamplesContribution() {
        this(0);
    }

    public EntitySamplesContribution(String name) {
        this(name, 0);
    }

    public EntitySamplesContribution(int order) {
        super();
        this.order = order;
    }

    public EntitySamplesContribution(String name, int order) {
        super(name);
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    protected abstract void preamble();

    protected synchronized void assemble() {
        if (assembled)
            return;

        preamble();

        assembled = true;
    }

    protected <E extends IEntity<K>, K extends Serializable> //
    void addNormalSample(E... samples) {
        for (IEntity<?> sample : samples) {
            addNormalSample(sample);
        }
        return;
    }

    protected void addNormalSample(IEntity<? extends Serializable> sample) {
        if (normalSamples == null) {
            synchronized (this) {
                if (normalSamples == null)
                    normalSamples = new ArrayList<IEntity<?>>();
            }
        }
        normalSamples.add(sample);
    }

    protected <E extends IEntity<K>, K extends Serializable> //
    void addWorseSample(E... samples) {
        for (IEntity<?> sample : samples) {
            addWorseSample(sample);
        }
        return;
    }

    protected void addWorseSample(IEntity<? extends Serializable> sample) {
        if (worseSamples == null) {
            synchronized (this) {
                if (worseSamples == null)
                    worseSamples = new ArrayList<IEntity<?>>();
            }
        }
        worseSamples.add(sample);
    }

    @Override
    public Collection<IEntity<?>> getTransientSamples(boolean worseCase) {

        assemble();

        if (worseCase) {
            if (worseSamples != null)
                return worseSamples;
            else
                return Collections.emptyList();
        } else {
            if (normalSamples != null)
                return normalSamples;
            else
                return Collections.emptyList();
        }
    }

}
