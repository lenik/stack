package com.bee32.plover.arch.generic;

import java.util.Map;

public interface IParameterizedType {

    Class<?> getBaseType();

    String getDisplayTypeName(Object instance);

    Map<String, Object> getParameters(Object instance);

    <T> T getFeature(Object instance, Class<T> featureClass);

}
