package com.bee32.sem.world.color;

import java.awt.Color;
import java.io.Serializable;

import com.bee32.plover.orm.entity.IPopulatable;

public class TrueColor
        implements Serializable, Cloneable, IPopulatable {

    private static final long serialVersionUID = 1L;

    static final int scale = 0x10000;
    static final int k256 = scale / 0x100;

    int red;
    int green;
    int blue;
    int alpha = scale;

    public TrueColor() {
    }

    public TrueColor(int rgb24) {
        setRGB24(rgb24);
    }

    public TrueColor(int rgba32, boolean withAlpha) {
        if (withAlpha)
            setRGBA32(rgba32);
        else
            setRGB24(rgba32);
    }

    public TrueColor(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public TrueColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public TrueColor(Color awtColor) {
        setAwtColor(awtColor);
    }

    @Override
    public TrueColor clone() {
        return new TrueColor(alpha, red, green, blue);
    }

    @Override
    public void populate(Object source) {
        TrueColor o = (TrueColor) source;
        alpha = o.alpha;
        red = o.red;
        green = o.green;
        blue = o.blue;
    }

    public long getLong() {
        long lval = (alpha << 48) | (red << 32) | (green << 16) | (blue);
        return lval;
    }

    public void setLong(long lval) {
        blue = (int) (lval & 0xffff);
        lval >>>= 16;
        green = (int) (lval & 0xffff);
        lval >>>= 16;
        red = (int) (lval & 0xffff);
        lval >>>= 16;
        alpha = (int) (lval & 0xffff);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRGBA32() {
        int color = 0;
        int ialpha = alpha / k256;
        color = (color << 8) | ialpha;
        int ired = red / k256;
        color = (color << 8) | ired;
        int igreen = green / k256;
        color = (color << 8) | igreen;
        int iblue = blue / k256;
        color = (color << 8) | iblue;
        return color;
    }

    public void setRGBA32(int color) {
        blue = (color & 0xff) * k256;
        color >>>= 8;
        green = (color & 0xff) * k256;
        color >>>= 8;
        red = (color & 0xff) * k256;
        color >>>= 8;
        alpha = (color & 0xff) * k256;
    }

    public int getRGB24() {
        int rgb24 = 0;
        int ired = red / k256;
        rgb24 = (rgb24 << 8) | ired;
        int igreen = green / k256;
        rgb24 = (rgb24 << 8) | igreen;
        int iblue = blue / k256;
        rgb24 = (rgb24 << 8) | iblue;
        return rgb24;
    }

    public void setRGB24(int rgb24) {
        blue = (rgb24 & 0xff) * k256;
        rgb24 >>>= 8;
        green = (rgb24 & 0xff) * k256;
        rgb24 >>>= 8;
        red = (rgb24 & 0xff) * k256;
        // Don't change alpha value
        // alpha = scale;
    }

    public Color getAwtColor() {
        Color awtColor = new Color(red / k256, green / k256, blue / k256, alpha / k256);
        return awtColor;
    }

    public void setAwtColor(Color awtColor) {
        if (awtColor == null)
            throw new NullPointerException("awtColor");
        red = awtColor.getRed() * k256;
        green = awtColor.getGreen() * k256;
        blue = awtColor.getBlue() * k256;
        alpha = awtColor.getAlpha() * k256;
    }

    public String getHtmlColor() {
        StringBuilder sb = new StringBuilder(7);
        sb.append('#');
        appendHex2(sb, red / k256);
        appendHex2(sb, green / k256);
        appendHex2(sb, blue / k256);
        String htmlColor = sb.toString();
        return htmlColor;
    }

    public void setHtmlColor(String htmlColor) {
        if (htmlColor == null)
            throw new NullPointerException("htmlColor");
        if (htmlColor.startsWith("#"))
            htmlColor = htmlColor.substring(1);
        int rgb24 = Integer.parseInt(htmlColor, 16);
        setRGB24(rgb24);
    }

    @Override
    public String toString() {
        return getHtmlColor();
    }

    private static final char[] tab = "0123456789abcdef".toCharArray();

    private static void appendHex2(StringBuilder sb, int num) {
        int low = num & 0xf;
        int high = (num >> 4) & 0xf;
        sb.append(tab[high]);
        sb.append(tab[low]);
    }

}
