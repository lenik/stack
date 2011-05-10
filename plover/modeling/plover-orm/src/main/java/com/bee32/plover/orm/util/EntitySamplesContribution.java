package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.Component;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;

@ComponentTemplate
@Lazy
public abstract class EntitySamplesContribution
        extends Component
        implements IEntitySamplesContribution {

    public static final String CONF_LOADED = "esc.loaded";

    private List<Entity<?>> normalSamples;
    private List<Entity<?>> worseSamples;

    private boolean assembled;
    private boolean loaded;

    public EntitySamplesContribution() {
        super();
    }

    public EntitySamplesContribution(String name) {
        super(name);
    }

    protected abstract void preamble();

    protected synchronized void assemble() {
        if (assembled)
            return;

        preamble();

        assembled = true;
    }

    protected <E extends Entity<K>, K extends Serializable> //
    void addNormalSample(E... samples) {
        for (Entity<?> sample : samples) {
            addNormalSample(sample);
        }
        return;
    }

    protected void addNormalSample(Entity<? extends Serializable> sample) {
        if (normalSamples == null) {
            synchronized (this) {
                if (normalSamples == null)
                    normalSamples = new ArrayList<Entity<?>>();
            }
        }
        normalSamples.add(sample);
    }

    protected <E extends Entity<?>> //
    void addWorseSample(E... samples) {
        for (Entity<?> sample : samples) {
            addWorseSample(sample);
        }
        return;
    }

    protected void addWorseSample(Entity<?> sample) {
        if (worseSamples == null) {
            synchronized (this) {
                if (worseSamples == null)
                    worseSamples = new ArrayList<Entity<?>>();
            }
        }
        worseSamples.add(sample);
    }

    @Override
    public Collection<Entity<?>> getTransientSamples(boolean worseCase) {

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

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void beginLoad() {
    }

    @Override
    public void endLoad() {
    }

}
