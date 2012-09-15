package com.bee32.plover.ox1.xp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.Entity;

/**
 * 计分用属性池-X15s
 *
 * 该属性池主要用于计分。
 *
 * This pool contains:
 * <ul>
 * <li>2 ints
 * <li>2 longs
 * <li>8 floats
 * <li>2 doubles
 * <li>1 date/time
 * </ul>
 */
@MappedSuperclass
public abstract class XPool15_Scores<Es extends Entity<?>>
        extends XPool<Es> {

    private static final long serialVersionUID = 1L;

    private Integer int1;
    private Integer int2;

    private Long long1;
    private Long long2;

    private Float float1;
    private Float float2;
    private Float float3;
    private Float float4;
    private Float float5;
    private Float float6;
    private Float float7;
    private Float float8;

    private Double double1;
    private Double double2;

    private Date date1;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool15_Scores)
            _populate((XPool15_Scores<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    protected void _populate(XPool15_Scores<? extends Entity<?>> o) {
        super._populate(o);
        int1 = o.int1;
        int2 = o.int2;
        long1 = o.long1;
        long2 = o.long2;
        float1 = o.float1;
        float2 = o.float2;
        float3 = o.float3;
        float4 = o.float4;
        float5 = o.float5;
        float6 = o.float6;
        float7 = o.float7;
        float8 = o.float8;
        double1 = o.double1;
        double2 = o.double2;
        date1 = o.date1;
    }

    /**
     * 属性 INT1
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    /**
     * 属性 INT2
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    /**
     * 属性 LONG1
     *
     * 长度为 8B 的扩展长整数属性。
     */
    @Column
    public Long getLong1() {
        return long1;
    }

    public void setLong1(Long long1) {
        this.long1 = long1;
    }

    /**
     * 属性 LONG2
     *
     * 长度为 8B 的扩展长整数属性。
     */
    @Column
    public Long getLong2() {
        return long2;
    }

    public void setLong2(Long long2) {
        this.long2 = long2;
    }

    /**
     * 属性 FLOAT1
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat1() {
        return float1;
    }

    public void setFloat1(Float float1) {
        this.float1 = float1;
    }

    /**
     * 属性 FLOAT2
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat2() {
        return float2;
    }

    public void setFloat2(Float float2) {
        this.float2 = float2;
    }

    /**
     * 属性 FLOAT3
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat3() {
        return float3;
    }

    public void setFloat3(Float float3) {
        this.float3 = float3;
    }

    /**
     * 属性 FLOAT4
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat4() {
        return float4;
    }

    public void setFloat4(Float float4) {
        this.float4 = float4;
    }

    /**
     * 属性 FLOAT5
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat5() {
        return float5;
    }

    public void setFloat5(Float float5) {
        this.float5 = float5;
    }

    /**
     * 属性 FLOAT6
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat6() {
        return float6;
    }

    public void setFloat6(Float float6) {
        this.float6 = float6;
    }

    /**
     * 属性 FLOAT7
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat7() {
        return float7;
    }

    public void setFloat7(Float float7) {
        this.float7 = float7;
    }

    /**
     * 属性 FLOAT8
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat8() {
        return float8;
    }

    public void setFloat8(Float float8) {
        this.float8 = float8;
    }

    /**
     * 属性 DOUBLE1
     *
     * 长度为 8B 的扩展双精度浮点数属性。
     */
    @Column
    public Double getDouble1() {
        return double1;
    }

    public void setDouble1(Double double1) {
        this.double1 = double1;
    }

    /**
     * 属性 DOUBLE2
     *
     * 长度为 8B 的扩展双精度浮点数属性。
     */
    @Column
    public Double getDouble2() {
        return double2;
    }

    public void setDouble2(Double double2) {
        this.double2 = double2;
    }

    /**
     * 属性 DATE1
     *
     * 长度为 8B 的扩展时间戳属性。
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

}
