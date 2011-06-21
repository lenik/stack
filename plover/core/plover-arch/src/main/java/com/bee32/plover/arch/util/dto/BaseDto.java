package com.bee32.plover.arch.util.dto;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.Flags32;

public abstract class BaseDto<S, C>
        extends BaseDto_S2<S, C> {

    private static final long serialVersionUID = 1L;

    protected Class<? extends S> sourceType;
    protected final Flags32 selection = new Flags32();

    protected BaseDto(Class<? extends S> sourceType) {
        initSourceType(sourceType);
    }

    /**
     * Full marshal by default.
     */
    public BaseDto() {
        this(-1);
    }

    public BaseDto(int selection) {
        initSourceType(ClassUtil.<S> infer1(getClass(), BaseDto.class, 0));
        this.selection.bits = selection;
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

    public int getSelection() {
        return selection.bits;
    }

    public void setSelection(int selection) {
        this.selection.bits = selection;
    }

}
