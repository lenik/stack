package com.bee32.zebra.io.stock;

import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.io.art.Artifact;

/**
 * 库位选项
 */
public class PlaceOption {

    Artifact artifact;
    Place place;
    boolean locked;
    String status;

    double reservation;
    int checkPeriod;
    long checkExpires;

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @label 永久库位
     */
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @label 推荐库位状态
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 安全库存
     * 
     * @label 库存安全保留量
     */
    @OfGroup(StdGroup.Schedule.class)
    public double getReservation() {
        return reservation;
    }

    public void setReservation(double reservation) {
        this.reservation = reservation;
    }

    /**
     * @label 盘点周期
     */
    @OfGroup(StdGroup.Schedule.class)
    public int getCheckPeriod() {
        return checkPeriod;
    }

    public void setCheckPeriod(int checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    /**
     * @label 下次盘点时间
     */
    @OfGroup(StdGroup.Schedule.class)
    public long getCheckExpires() {
        return checkExpires;
    }

    public void setCheckExpires(long checkExpires) {
        this.checkExpires = checkExpires;
    }

}
