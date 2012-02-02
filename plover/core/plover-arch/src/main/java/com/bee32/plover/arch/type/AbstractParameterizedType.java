package com.bee32.plover.arch.type;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bee32.plover.arch.generic.IParameterizedType;

public abstract class AbstractParameterizedType
        implements IParameterizedType {

    final Class<?> baseType;

    public AbstractParameterizedType(Class<?> baseType) {
        if (baseType == null)
            throw new NullPointerException("baseType");
        this.baseType = baseType;
    }

    @Override
    public Class<?> getBaseType() {
        return baseType;
    }

    @Override
    public Map<String, Object> getParameters(Object instance) {
        Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        populateParameterMap(instance, parameterMap);
        return parameterMap;
    }

    protected void populateParameterMap(Object instance, Map<String, Object> parameterMap) {
    }

    @Override
    public <T> T getFeature(Object instance, Class<T> featureClass) {
        @SuppressWarnings("unchecked")
        T self = (T) this;
        if (featureClass.isAssignableFrom(getClass()))
            return self;
        else
            return null;
    }

}
