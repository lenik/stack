package com.bee32.sem.pricing.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ValveTypes
        extends StandardSamples {

    public final ValveType SIMPLE_SQUARE = new ValveType(10, "SIMPLE-SQUARE", "简单方形");
    public final ValveType WALL_TYPE = new ValveType(20, "WALL-TYPE", "壁式");
    public final ValveType WALL_TYPE_WITH_PANEL = new ValveType(30, "WALL-TYPE-WITH-PANEL", "壁式含面板");
    public final ValveType CIRCLE = new ValveType(40, "CIRCLE", "圆型");
}
