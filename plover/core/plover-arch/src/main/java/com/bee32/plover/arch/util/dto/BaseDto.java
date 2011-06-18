package com.bee32.plover.arch.util.dto;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.Flags32;

public abstract class BaseDto<S, C>
        extends BaseDto_VTU<S, C> {

    private static final long serialVersionUID = 1L;

    protected Class<? extends S> sourceType;
    protected final Flags32 selection = new Flags32();

    protected BaseDto(Class<? extends S> sourceType) {
        initSourceType(sourceType);
    }

    public BaseDto() {
        initSourceType(ClassUtil.<S> infer1(getClass(), BaseDto.class, 0));
    }

    public BaseDto(S source) {
        this();
        if (source != null)
            marshal(source);
    }

    public BaseDto(int selection) {
        this();
        this.selection.set(selection);
    }

    public BaseDto(int selection, S source) {
        this(selection);
        if (source != null)
            marshal(source);
    }

    /**
     * When it's a generic DTO, then the source type is not specialized yet. So there should be a
     * chance to change it.
     *
     * One should override {@link #initSourceType(Class)} as public so it may be changed by user.
     *
     * @param sourceType
     *            New source type.
     */
    @SuppressWarnings("unchecked")
    protected void initSourceType(Class<? extends S> sourceType) {
        // XXX cast?
        this.sourceType = (Class<S>) sourceType;
    }

}
