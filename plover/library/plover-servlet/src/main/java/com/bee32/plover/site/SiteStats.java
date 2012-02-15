package com.bee32.plover.site;

import java.io.Serializable;

public class SiteStats
        implements Serializable {

    private static final long serialVersionUID = 1L;

    long requestCount;
    long serviceTime;
    long otherRequestCount;
    long otherServiceTime;
    long bee32RequestCount;
    long bee32ServiceTime;
    long microRequestCount;
    long microServiceTime;

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

    public void serviceComplete(long serviceTime, boolean bee32, boolean micro) {
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
