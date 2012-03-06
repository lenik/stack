package com.bee32.plover.ox1.xp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.Entity;

/**
 * XPool30 - General-purpose XPool.
 *
 * This pool contains:
 * <ul>
 * <li>1 bit-group
 * <li>4 ints
 * <li>2 longs
 * <li>2 floats
 * <li>2 doubles
 * <li>2 date/time
 * <li>5 char[LEN_A]
 * <li>2 char[LEN_AA]
 * </ul>
 */
@MappedSuperclass
public abstract class XPool20<Es extends Entity<?>>
        extends XPool<Es> {

    private static final long serialVersionUID = 1L;

    int bits;

    Integer int1;
    Integer int2;
    Integer int3;
    Integer int4;

    Long long1;
    Long long2;

    Float float1;
    Float float2;

    Double double1;
    Double double2;

    Date date1;
    Date date2;

    String a1;
    String a2;
    String a3;
    String a4;
    String a5;

    String aa1;
    String aa2;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool20)
            _populate((XPool20<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    protected void _populate(XPool20<? extends Entity<?>> o) {
        super._populate(o);
        bits = o.bits;
        int1 = o.int1;
        int2 = o.int2;
        int3 = o.int3;
        int4 = o.int4;
        long1 = o.long1;
        long2 = o.long2;
        float1 = o.float1;
        float2 = o.float2;
        double1 = o.double1;
        double2 = o.double2;
        date1 = o.date1;
        date2 = o.date2;
        a1 = o.a1;
        a2 = o.a2;
        a3 = o.a3;
        a4 = o.a4;
        a5 = o.a5;
        aa1 = o.aa1;
        aa2 = o.aa2;
    }

    @Column(nullable = false)
    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    @Column
    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    @Column
    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    @Column
    public Integer getInt3() {
        return int3;
    }

    public void setInt3(Integer int3) {
        this.int3 = int3;
    }

    @Column
    public Integer getInt4() {
        return int4;
    }

    public void setInt4(Integer int4) {
        this.int4 = int4;
    }

    @Column
    public Long getLong1() {
        return long1;
    }

    public void setLong1(Long long1) {
        this.long1 = long1;
    }

    @Column
    public Long getLong2() {
        return long2;
    }

    public void setLong2(Long long2) {
        this.long2 = long2;
    }

    @Column
    public Float getFloat1() {
        return float1;
    }

    public void setFloat1(Float float1) {
        this.float1 = float1;
    }

    @Column
    public Float getFloat2() {
        return float2;
    }

    public void setFloat2(Float float2) {
        this.float2 = float2;
    }

    @Column
    public Double getDouble1() {
        return double1;
    }

    public void setDouble1(Double double1) {
        this.double1 = double1;
    }

    @Column
    public Double getDouble2() {
        return double2;
    }

    public void setDouble2(Double double2) {
        this.double2 = double2;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    @Column(length = LEN_A)
    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    @Column(length = LEN_A)
    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    @Column(length = LEN_A)
    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    @Column(length = LEN_A)
    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    @Column(length = LEN_A)
    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    @Column(length = LEN_AA)
    public String getAa1() {
        return aa1;
    }

    public void setAa1(String aa1) {
        this.aa1 = aa1;
    }

    @Column(length = LEN_AA)
    public String getAa2() {
        return aa2;
    }

    public void setAa2(String aa2) {
        this.aa2 = aa2;
    }

}
