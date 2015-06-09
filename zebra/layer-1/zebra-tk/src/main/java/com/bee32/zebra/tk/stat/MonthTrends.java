package com.bee32.zebra.tk.stat;

import net.bodz.bas.t.pojo.Pair;
import net.bodz.lily.model.base.CoObject;

public class MonthTrends
        extends CoObject {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    long count;
    double sum1;
    double sum2;

    public MonthTrends() {
    }

    @Override
    public Object getId() {
        return Pair.of(year, month);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum1() {
        return sum1;
    }

    public void setSum1(double sum1) {
        this.sum1 = sum1;
    }

    public double getSum2() {
        return sum2;
    }

    public void setSum2(double sum2) {
        this.sum2 = sum2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(year);
        sb.append("-");
        sb.append(month);
        sb.append(": ");
        sb.append(count);
        return sb.toString();
    }

}
