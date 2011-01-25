package com.bee32.plover.cache.pull;

public interface ICacheSchema {

    int tick();

    int compareSerialVersion(int a, int b);

}
