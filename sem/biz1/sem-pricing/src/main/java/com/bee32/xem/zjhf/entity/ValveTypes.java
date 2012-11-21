package com.bee32.xem.zjhf.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ValveTypes
        extends StandardSamples {

    public final ValveType SIMPLE_SQUARE = new ValveType("简单方形", "");
    public final ValveType WALL_TYPE = new ValveType("壁式", "");
    public final ValveType WALL_TYPE_WITH_PANEL = new ValveType("壁式含面板", "");
    public final ValveType CIRCLE = new ValveType("圆型", "");
}
