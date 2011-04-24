package com.bee32.plover.arch.util;

public class DTOs
        extends DataTransferObject<Void> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(Void source) {
    }

    @Override
    protected void _unmarshalTo(Void target) {
    }

    public static Class<?> getSourceType(DataTransferObject<?> dto) {
        return dto.sourceType;
    }

}
