package com.bee32.plover.inject.util;

import java.awt.List;

import org.junit.Assert;
import org.junit.Test;

public class NameQualifiedClassMapTest
        extends Assert
        implements NQCLiterals {

    NameQualifiedClassMap<String> map;

    public NameQualifiedClassMapTest() {
        map = new NameQualifiedClassMap<String>();
        map.put(fooList, "fooList");
        map.put(barList, "barList");
        map.put(foobarList, "foobarList");
        map.put(fooArrayList, "fooArrayList");
        map.put(foobarArrayList, "foobarArrayList");
    }

    @Test
    public void testFloor() {
        // XXX - 把链上的元素全部推进去，
        // 这样当请求缺省的 x 实现时，可以取得第一个子节点。

        // 另外，用一个 PreorderMap + 一个 default-inheritance-map
        // 用两个 map ** 不能解决一致性问题，一致性的问题很复杂，暂且搁置再说。
        String floor = map.floor(List.class);
        assertNull(floor);
    }

}
