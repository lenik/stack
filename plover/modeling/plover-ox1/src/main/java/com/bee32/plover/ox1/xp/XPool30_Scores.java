package com.bee32.plover.ox1.xp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.Entity;

/**
 * 计分用属性池-X30s
 *
 * 该属性池主要用于计分。
 *
 * This pool contains:
 * <ul>
 * <li>4 ints
 * <li>4 longs
 * <li>16 floats
 * <li>4 doubles
 * <li>2 date/time
 * </ul>
 */
@MappedSuperclass
public abstract class XPool30_Scores<Es extends Entity<?>>
        extends XPool15_Scores<Es> {

    private static final long serialVersionUID = 1L;

    private Integer int3;
    private Integer int4;

    private Long long3;
    private Long long4;

    private Float float9;
    private Float float10;
    private Float float11;
    private Float float12;
    private Float float13;
    private Float float14;
    private Float float15;
    private Float float16;

    private Double double3;
    private Double double4;

    private Date date2;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool30_Scores)
            _populate((XPool30_Scores<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    protected void _populate(XPool30_Scores<? extends Entity<?>> o) {
        super._populate(o);
        int3 = o.int3;
        int4 = o.int4;
        long3 = o.long3;
        long4 = o.long4;
        float9 = o.float9;
        float10 = o.float10;
        float11 = o.float11;
        float12 = o.float12;
        float13 = o.float13;
        float14 = o.float14;
        float15 = o.float15;
        float16 = o.float16;
        double3 = o.double3;
        double4 = o.double4;
        date2 = o.date2;
    }

    /**
     * 属性 INT3
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt3() {
        return int3;
    }

    public void setInt3(Integer int3) {
        this.int3 = int3;
    }

    /**
     * 属性 INT4
     *
     * 长度为 4B 的扩展整数属性。
     */
    @Column
    public Integer getInt4() {
        return int4;
    }

    public void setInt4(Integer int4) {
        this.int4 = int4;
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
     * 属性 FLOAT9
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat9() {
        return float9;
    }

    public void setFloat9(Float float9) {
        this.float9 = float9;
    }

    /**
     * 属性 FLOAT10
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat10() {
        return float10;
    }

    public void setFloat10(Float float10) {
        this.float10 = float10;
    }

    /**
     * 属性 FLOAT11
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat11() {
        return float11;
    }

    public void setFloat11(Float float11) {
        this.float11 = float11;
    }

    /**
     * 属性 FLOAT12
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat12() {
        return float12;
    }

    public void setFloat12(Float float12) {
        this.float12 = float12;
    }

    /**
     * 属性 FLOAT13
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat13() {
        return float13;
    }

    public void setFloat13(Float float13) {
        this.float13 = float13;
    }

    /**
     * 属性 FLOAT14
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat14() {
        return float14;
    }

    public void setFloat14(Float float14) {
        this.float14 = float14;
    }

    /**
     * 属性 FLOAT15
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat15() {
        return float15;
    }

    public void setFloat15(Float float15) {
        this.float15 = float15;
    }

    /**
     * 属性 FLOAT16
     *
     * 长度为 4B 的扩展浮点数属性。
     */
    @Column
    public Float getFloat16() {
        return float16;
    }

    public void setFloat16(Float float16) {
        this.float16 = float16;
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
     * 属性 DATE2
     *
     * 长度为 8B 的扩展时间戳属性。
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

}
