package com.bee32.plover.arch.util;

import java.util.Map;

public interface IStruct
        extends Map<String, Object> {

    <T> T getScalar(String key);

    <T> T[] getArray(String key);

}
