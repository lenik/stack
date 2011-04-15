package com.bee32.plover.arch.util;

public class DTOUtil {

    public static Class<?> getDataType(DataTransferObject<?> dto) {
        return dto.dataType;
    }

}
