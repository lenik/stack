package com.bee32.plover.ox1.xp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

/**
 * 属性池-X30
 *
 * 该属性池比 X20 属性池多出 10 个混合字段。
 *
 * XPool30 - General-purpose XPool.
 *
 * This pool contains:
 * <ul>
 * <li>1 bit-group
 * <li>8 ints
 * <li>4 longs
 * <li>4 floats
 * <li>4 doubles
 * <li>2 date/time
 * <li>5 char[32]
 * <li>2 char[64]
 * <li>1 char[250]
 * </ul>
 */
@MappedSuperclass
public abstract class XPool30<Es extends Entity<?>>
        extends XPool20<Es> {

    private static final long serialVersionUID = 1L;

    Integer int5;
    Integer int6;
    Integer int7;
    Integer int8;

    Long long3;
    Long long4;

    Float float3;
    Float float4;

    Double double3;
    Double double4;

    String aaaa1;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool30)
            _populate((XPool30<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    protected void _populate(XPool30<? extends Entity<?>> o) {
        super._populate(o);
        int5 = o.int5;
        int6 = o.int6;
        int7 = o.int7;
        int8 = o.int8;
        long3 = o.long3;
        long4 = o.long4;
        float3 = o.float3;
        float4 = o.float4;
        double3 = o.double3;
        double4 = o.double4;
        aaaa1 = o.aaaa1;
    }

    /**
     * 属性 INT5
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt5() {
        return int5;
    }

    public void setInt5(Integer int5) {
        this.int5 = int5;
    }

    /**
     * 属性 INT6
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt6() {
        return int6;
    }

    public void setInt6(Integer int6) {
        this.int6 = int6;
    }

    /**
     * 属性 INT7
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt7() {
        return int7;
    }

    public void setInt7(Integer int7) {
        this.int7 = int7;
    }

    /**
     * 属性 INT8
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt8() {
        return int8;
    }

    public void setInt8(Integer int8) {
        this.int8 = int8;
    }

    /**
     * 属性 LONG3
     *
     * 长度为 8B 的扩展长整数属性。
     */
    @Column
    public Long getLong3() {
        return long3;
    }

    public void setLong3(Long long3) {
        this.long3 = long3;
    }

    /**
     * 属性 LONG4
     *
     * 长度为 8B 的扩展长整数属性。
     */
    @Column
    public Long getLong4() {
        return long4;
    }

    public void setLong4(Long long4) {
        this.long4 = long4;
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
     * 属性 DOUBLE3
     *
     * 长度为 8B 的扩展双精度浮点数属性。
     */
    @Column
    public Double getDouble3() {
        return double3;
    }

    public void setDouble3(Double double3) {
        this.double3 = double3;
    }

    /**
     * 属性 DOUBLE4
     *
     * 长度为 8B 的扩展双精度浮点数属性。
     */
    @Column
    public Double getDouble4() {
        return double4;
    }

    public void setDouble4(Double double4) {
        this.double4 = double4;
    }

    /**
     * 属性 AAAA1
     *
     * 长度为 250 的扩展字符串属性。
     */
    @Column(length = LEN_AAAA)
    public String getAaaa1() {
        return aaaa1;
    }

    public void setAaaa1(String aaaa1) {
        this.aaaa1 = aaaa1;
    }

}
