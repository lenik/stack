package com.bee32.sem.world.thing;

import com.bee32.plover.orm.sample.StandardSamples;

public class Units
        extends StandardSamples {

    public final Unit GRAM = new Unit("g", "克", "质量");
    public final Unit KILOGRAM = new Unit("kg", "千克", 1000, GRAM);
    public final Unit MILLIGRAM = new Unit("mg", "毫克", 0.001, GRAM);
    public final Unit TON = new Unit("t", "吨", 1000000, GRAM);
    public final Unit POUND = new Unit("lb", "磅", 453.59237, GRAM); // 16 ounce
    public final Unit OUNCE = new Unit("oz", "盎司", 28.3495231, GRAM);
    public final Unit CARET = new Unit("ct", "克拉", 0.2, GRAM);

    public final Unit NEWTON = new Unit("N", "牛顿", "力");
    public final Unit KILONEWTON = new Unit("kN", "千牛顿", 1000, NEWTON);

    public final Unit METER = new Unit("m", "米", "长度");
    public final Unit MILLIMETER = new Unit("mm", "毫米", 0.001, METER);
    public final Unit CENTIMETER = new Unit("cm", "厘米", 0.01, METER);
    public final Unit DECIMETER = new Unit("dm", "分米", 0.1, METER);
    public final Unit KILOMETER = new Unit("km", "千米", 1000, METER);

    public final Unit CUBIC_METER = new Unit("m3", "立方米", "体积");

    public final Unit LITER = new Unit("L", "升", 0.001, CUBIC_METER, "容量");
    public final Unit MILLILITER = new Unit("mL", "毫升", 0.000001, CUBIC_METER, "容量");

    public final Unit SQUARE_METER = new Unit("m2", "平方米", "面积");
    public final Unit SQUARE_CENTIMETER = new Unit("cm2", "平方厘米", 0.0001, SQUARE_METER);
    public final Unit SQUARE_KILOMETER = new Unit("km2", "平方千米", 1000000, SQUARE_METER);

    public final Unit PIECE = new Unit("PCS", "个", "数量");
    public final Unit P_TAI = new Unit("台", "台", 1, PIECE);
    public final Unit P_ZHANG = new Unit("张", "张", 1, PIECE);
    public final Unit P_ZHI = new Unit("只", "只", 1, PIECE);
    public final Unit P_ZHI2 = new Unit("支", "支", 1, PIECE);
    public final Unit P_TAO = new Unit("套", "套", 1, PIECE);
    public final Unit P_PING = new Unit("瓶", "瓶", 1, PIECE);
    public final Unit P_TONG = new Unit("桶", "桶", 1, PIECE);
    public final Unit P_XIANG = new Unit("箱", "箱", 1, PIECE);
    public final Unit P_LI = new Unit("粒", "粒", 1, PIECE);
    public final Unit P_TIAO = new Unit("条", "条", 1, PIECE);
    public final Unit P_HE = new Unit("盒", "盒", 1, PIECE);

}
