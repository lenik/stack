package com.bee32.plover.arch.util;

import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.NOPTransformer;
import org.apache.commons.collections15.map.TransformedMap;

public class GetOpenedObjectTransformer {

    static final Transformer<IOpenedObjectHolder, Object> GET_OPENED_OBJECT = new Transformer<IOpenedObjectHolder, Object>() {

        @Override
        public Object transform(IOpenedObjectHolder input) {
            if (input == null) // assert false?
                return null;
            else
                return input.getOpenedObject();
        }

    };

    public static <K> Map<K, Object> decorate(Map<K, ? extends IOpenedObjectHolder> orig) {
        return TransformedMap.decorate(orig, NOPTransformer.INSTANCE, GET_OPENED_OBJECT);
    }

}
