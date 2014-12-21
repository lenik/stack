package com.bee32.zebra.io.art;

public class UOMs {

    public static final UOM GRAM = new UOM("g", "克", "质量");
    public static final UOM KILOGRAM = new UOM("kg", "千克", 1000, GRAM);
    public static final UOM MILLIGRAM = new UOM("mg", "毫克", 0.001, GRAM);
    public static final UOM TON = new UOM("t", "吨", 1000000, GRAM);
    public static final UOM POUND = new UOM("lb", "磅", 453.59237, GRAM); // 16 ounce
    public static final UOM OUNCE = new UOM("oz", "盎司", 28.3495231, GRAM);
    public static final UOM CARET = new UOM("ct", "克拉", 0.2, GRAM);

    public static final UOM NEWTON = new UOM("N", "牛顿", "力");
    public static final UOM KILONEWTON = new UOM("kN", "千牛顿", 1000, NEWTON);

    public static final UOM METER = new UOM("m", "米", "长度");
    public static final UOM MILLIMETER = new UOM("mm", "毫米", 0.001, METER);
    public static final UOM CENTIMETER = new UOM("cm", "厘米", 0.01, METER);
    public static final UOM DECIMETER = new UOM("dm", "分米", 0.1, METER);
    public static final UOM KILOMETER = new UOM("km", "千米", 1000, METER);

    public static final UOM CUBIC_METER = new UOM("m3", "立方米", "体积");
    public static final UOM LITER = new UOM("L", "升", 0.001, CUBIC_METER, "容量");
    public static final UOM MILLILITER = new UOM("mL", "毫升", 0.000001, CUBIC_METER, "容量");

    public static final UOM SQUARE_METER = new UOM("m2", "平方米", "面积");
    public static final UOM SQUARE_CENTIMETER = new UOM("cm2", "平方厘米", 0.0001, SQUARE_METER);
    public static final UOM SQUARE_KILOMETER = new UOM("km2", "平方千米", 1000000, SQUARE_METER);

    public static final UOM PIECE = new UOM("pcs", "个", "数量");
    public static final UOM P_TAI = new UOM("台", "台", 1, PIECE);
    public static final UOM P_ZHANG = new UOM("张", "张", 1, PIECE);
    public static final UOM P_ZHI = new UOM("只", "只", 1, PIECE);
    public static final UOM P_ZHI2 = new UOM("支", "支", 1, PIECE);
    public static final UOM P_TAO = new UOM("套", "套", 1, PIECE);
    public static final UOM P_PING = new UOM("瓶", "瓶", 1, PIECE);
    public static final UOM P_TONG = new UOM("桶", "桶", 1, PIECE);
    public static final UOM P_XIANG = new UOM("箱", "箱", 1, PIECE);
    public static final UOM P_LI = new UOM("粒", "粒", 1, PIECE);
    public static final UOM P_TIAO = new UOM("条", "条", 1, PIECE);
    public static final UOM P_HE = new UOM("盒", "盒", 1, PIECE);

}
