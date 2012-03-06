package com.bee32.plover.ox1.xp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.Entity;

/**
 * Score Pool.
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
        extends XPool<Es> {

    private static final long serialVersionUID = 1L;

    Integer int1;
    Integer int2;
    Integer int3;
    Integer int4;

    Long long1;
    Long long2;
    Long long3;
    Long long4;

    Float float1;
    Float float2;
    Float float3;
    Float float4;
    Float float5;
    Float float6;
    Float float7;
    Float float8;
    Float float9;
    Float float10;
    Float float11;
    Float float12;
    Float float13;
    Float float14;
    Float float15;
    Float float16;

    Double double1;
    Double double2;
    Double double3;
    Double double4;

    Date date1;
    Date date2;

X-Population

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
    public Long getLong3() {
        return long3;
    }

    public void setLong3(Long long3) {
        this.long3 = long3;
    }

    @Column
    public Long getLong4() {
        return long4;
    }

    public void setLong4(Long long4) {
        this.long4 = long4;
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
    public Float getFloat3() {
        return float3;
    }

    public void setFloat3(Float float3) {
        this.float3 = float3;
    }

    @Column
    public Float getFloat4() {
        return float4;
    }

    public void setFloat4(Float float4) {
        this.float4 = float4;
    }

    @Column
    public Float getFloat5() {
        return float5;
    }

    public void setFloat5(Float float5) {
        this.float5 = float5;
    }

    @Column
    public Float getFloat6() {
        return float6;
    }

    public void setFloat6(Float float6) {
        this.float6 = float6;
    }

    @Column
    public Float getFloat7() {
        return float7;
    }

    public void setFloat7(Float float7) {
        this.float7 = float7;
    }

    @Column
    public Float getFloat8() {
        return float8;
    }

    public void setFloat8(Float float8) {
        this.float8 = float8;
    }

    @Column
    public Float getFloat9() {
        return float9;
    }

    public void setFloat9(Float float9) {
        this.float9 = float9;
    }

    @Column
    public Float getFloat10() {
        return float10;
    }

    public void setFloat10(Float float10) {
        this.float10 = float10;
    }

    @Column
    public Float getFloat11() {
        return float11;
    }

    public void setFloat11(Float float11) {
        this.float11 = float11;
    }

    @Column
    public Float getFloat12() {
        return float12;
    }

    public void setFloat12(Float float12) {
        this.float12 = float12;
    }

    @Column
    public Float getFloat13() {
        return float13;
    }

    public void setFloat13(Float float13) {
        this.float13 = float13;
    }

    @Column
    public Float getFloat14() {
        return float14;
    }

    public void setFloat14(Float float14) {
        this.float14 = float14;
    }

    @Column
    public Float getFloat15() {
        return float15;
    }

    public void setFloat15(Float float15) {
        this.float15 = float15;
    }

    @Column
    public Float getFloat16() {
        return float16;
    }

    public void setFloat16(Float float16) {
        this.float16 = float16;
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

    @Column
    public Double getDouble3() {
        return double3;
    }

    public void setDouble3(Double double3) {
        this.double3 = double3;
    }

    @Column
    public Double getDouble4() {
        return double4;
    }

    public void setDouble4(Double double4) {
        this.double4 = double4;
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

}
