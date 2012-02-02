package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Set;

import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.GeneralFormatter;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class PartialDto<T extends Serializable>
        extends BaseDto_EMC<T> {

    private static final long serialVersionUID = 1L;

    public PartialDto() {
        super();
    }

    public PartialDto(int selection) {
        super(selection);
    }

    @Override
    public <D extends BaseDto<?, ?>> D ref(T source) {
        throw new UnsupportedOperationException("Partial/dto doesn't have a key for reference.");
    }

    @Override
    protected boolean idEquals(BaseDto<T, IEntityMarshalContext> other) {
        return this == other;
    }

    @Override
    protected int idHashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public Serializable getKey() {
        return this;
    }

    @Override
    public boolean isNullRef() {
        return false;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        GeneralFormatter formatter = new GeneralFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

}
