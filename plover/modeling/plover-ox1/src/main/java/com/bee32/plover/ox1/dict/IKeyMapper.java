package com.bee32.plover.ox1.dict;

public interface IKeyMapper<T, K> {

    K getKey(T obj);

}
