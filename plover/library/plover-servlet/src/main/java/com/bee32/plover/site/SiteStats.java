package com.bee32.plover.site;

import java.io.Serializable;

public class SiteStats
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private long groups;
    private long startupCost;
    private long requestCount;
    private long serviceTime;
    private long otherRequestCount;
    private long otherServiceTime;
    private long bee32RequestCount;
    private long bee32ServiceTime;
    private long microRequestCount;
    private long microServiceTime;

    public long getGroups() {
        return groups;
    }

    public long getStartupCost() {
        return startupCost;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public float getMeanServiceTime() {
        if (requestCount == 0)
            return Float.NaN;
        else
            return (float) serviceTime / (float) requestCount;
    }

    public long getOtherRequestCount() {
        return otherRequestCount;
    }

    public long getOtherServiceTime() {
        return otherServiceTime;
    }

    public float getMeanOtherServiceTime() {
        if (otherRequestCount == 0)
            return Float.NaN;
        else
            return (float) otherServiceTime / (float) otherRequestCount;
    }

    public long getBee32RequestCount() {
        return bee32RequestCount;
    }

    public long getBee32ServiceTime() {
        return bee32ServiceTime;
    }

    public float getMeanBee32ServiceTime() {
        if (bee32RequestCount == 0)
            return Float.NaN;
        else
            return (float) bee32ServiceTime / (float) bee32RequestCount;
    }

    public long getMicroRequestCount() {
        return microRequestCount;
    }

    public long getMicroServiceTime() {
        return microServiceTime;
    }

    public float getMeanMicroServiceTime() {
        if (microRequestCount == 0)
            return Float.NaN;
        else
            return (float) microServiceTime / (float) microRequestCount;
    }

    public void addGroup(SiteStats group) {
        groups++;
        startupCost += group.startupCost;
        requestCount += group.requestCount;
        serviceTime += group.serviceTime;
        bee32RequestCount += group.bee32RequestCount;
        bee32ServiceTime += group.bee32ServiceTime;
        microRequestCount += group.microRequestCount;
        microServiceTime += group.microServiceTime;
        otherRequestCount += group.otherRequestCount;
        otherServiceTime += group.otherServiceTime;
    }

    public void addStartup(long startupCost) {
        startupCost += startupCost;
    }

    public void addService(long serviceTime, boolean bee32, boolean micro) {
        this.requestCount++;
        this.serviceTime += serviceTime;
        if (bee32) {
            this.bee32RequestCount++;
            this.bee32ServiceTime += serviceTime;
        }
        if (micro) {
            this.microRequestCount++;
            this.microServiceTime += serviceTime;
        }
        if (!bee32 && !micro) {
            this.otherRequestCount++;
            this.otherServiceTime += serviceTime;
        }
    }

}
