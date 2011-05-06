package com.bee32.plover.orm.ext.xp;

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
        extends XPool<Es> {

    private static final long serialVersionUID = 1L;

    int bits;

    Integer int1;
    Integer int2;
    Integer int3;
    Integer int4;
    Integer int5;
    Integer int6;
    Integer int7;
    Integer int8;

    Long long1;
    Long long2;
    Long long3;
    Long long4;

    Float float1;
    Float float2;
    Float float3;
    Float float4;

    Double double1;
    Double double2;
    Double double3;
    Double double4;

    Date date1;
    Date date2;

    String a1;
    String a2;
    String a3;
    String a4;
    String a5;

    String aa1;
    String aa2;

    String aaaa1;

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
    public Integer getInt5() {
        return int5;
    }

    public void setInt5(Integer int5) {
        this.int5 = int5;
    }

    @Column
    public Integer getInt6() {
        return int6;
    }

    public void setInt6(Integer int6) {
        this.int6 = int6;
    }

    @Column
    public Integer getInt7() {
        return int7;
    }

    public void setInt7(Integer int7) {
        this.int7 = int7;
    }

    @Column
    public Integer getInt8() {
        return int8;
    }

    public void setInt8(Integer int8) {
        this.int8 = int8;
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

    @Column(length = 32)
    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    @Column(length = 32)
    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    @Column(length = 32)
    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    @Column(length = 32)
    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    @Column(length = 32)
    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    @Column(length = 64)
    public String getAa1() {
        return aa1;
    }

    public void setAa1(String aa1) {
        this.aa1 = aa1;
    }

    @Column(length = 64)
    public String getAa2() {
        return aa2;
    }

    public void setAa2(String aa2) {
        this.aa2 = aa2;
    }

    @Column(length = 250)
    public String getAaaa1() {
        return aaaa1;
    }

    public void setAaaa1(String aaaa1) {
        this.aaaa1 = aaaa1;
    }

}
