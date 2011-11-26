package com.bee32.plover.ox1.dict;

import java.util.Collection;

import javax.free.IPreorder;
import javax.free.ITransformer;

public class PreorderTreeConverter<K> {

    ITransformer<Object, K> mapper;
    IPreorder<K> preorder;

    public void convert(Collection<?> collection) {
        for (Object obj : collection) {
            K key = (K) mapper.transform(obj);
            K preceding = preorder.getPreceding(key);
        }
    }

    private void mapper(Object obj) {
    }

}
