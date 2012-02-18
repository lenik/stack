package com.bee32.plover.site;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.JavaioFile;
import javax.free.XMLs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.logging.ExceptionLogEntry;

public class SiteStats
        implements Serializable {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(SiteStats.class);

    private int groups;
    private int exceptions;

    private Long startupTime;
    private long startupCost;
    private Long runTime;
    private long requestCount;
    private long serviceTime;
    private long otherRequestCount;
    private long otherServiceTime;
    private long bee32RequestCount;
    private long bee32ServiceTime;
    private long microRequestCount;
    private long microServiceTime;

    private SiteStats parent;
    private List<SiteStats> children = new ArrayList<SiteStats>();

    public SiteStats() {
        this(1);
    }

    public SiteStats(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            SiteStats simpleChild = new SiteStats(0 /* End-term */);
            simpleChild.parent = this;
            children.add(simpleChild);
        }
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public int getExceptions() {
        return exceptions;
    }

    public void setExceptions(int exceptions) {
        this.exceptions = exceptions;
    }

    public Long getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(Long startupTime) {
        this.startupTime = startupTime;
    }

    public long getStartupCost() {
        return startupCost;
    }

    public void setStartupCost(long startupCost) {
        this.startupCost = startupCost;
    }

    public Long getRunTime() {
        if (runTime != null)
            return runTime; // sum children up here.
        if (startupTime == null)
            return null;
        return System.currentTimeMillis() - startupTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
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

    public void setOtherRequestCount(long otherRequestCount) {
        this.otherRequestCount = otherRequestCount;
    }

    public long getOtherServiceTime() {
        return otherServiceTime;
    }

    public void setOtherServiceTime(long otherServiceTime) {
        this.otherServiceTime = otherServiceTime;
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

    public void setBee32RequestCount(long bee32RequestCount) {
        this.bee32RequestCount = bee32RequestCount;
    }

    public long getBee32ServiceTime() {
        return bee32ServiceTime;
    }

    public void setBee32ServiceTime(long bee32ServiceTime) {
        this.bee32ServiceTime = bee32ServiceTime;
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

    public void setMicroRequestCount(long microRequestCount) {
        this.microRequestCount = microRequestCount;
    }

    public long getMicroServiceTime() {
        return microServiceTime;
    }

    public void setMicroServiceTime(long microServiceTime) {
        this.microServiceTime = microServiceTime;
    }

    public float getMeanMicroServiceTime() {
        if (microRequestCount == 0)
            return Float.NaN;
        else
            return (float) microServiceTime / (float) microRequestCount;
    }

    public SiteStats getParent() {
        return parent;
    }

    public void setParent(SiteStats parent) {
        this.parent = parent;
    }

    public List<SiteStats> getChildren() {
        return children;
    }

    public void setChildren(List<SiteStats> children) {
        this.children = children;
    }

    public SiteStats getLastChild() {
        if (children.isEmpty())
            return null;
        else
            return children.get(children.size() - 1);
    }

    public void addGroup(SiteStats group) {
        groups++;
        startupCost += group.startupCost;
        runTime += group.runTime;
        requestCount += group.requestCount;
        serviceTime += group.serviceTime;
        bee32RequestCount += group.bee32RequestCount;
        bee32ServiceTime += group.bee32ServiceTime;
        microRequestCount += group.microRequestCount;
        microServiceTime += group.microServiceTime;
        otherRequestCount += group.otherRequestCount;
        otherServiceTime += group.otherServiceTime;
        if (parent != null)
            parent.addGroup(group);
    }

    public void addException(ExceptionLogEntry entry) {
        exceptions++;
        if (parent != null)
            parent.addException(entry);
    }

    public void addStartup(long startupCost) {
        startupCost += startupCost;
        if (parent != null)
            parent.addStartup(startupCost);
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
        if (parent != null)
            parent.addService(serviceTime, bee32, micro);
    }

    public void saveToFile(File statsFile)
            throws IOException {
        String xml = XMLs.encode(this);
        new JavaioFile(statsFile).forWrite().write(xml);
    }

    public static SiteStats readFromFile(File statsFile)
            throws IOException {
        SiteStats stats = null;
        if (statsFile != null && statsFile.exists())
            try {
                String statsXml = new JavaioFile(statsFile).forRead().readTextContents();
                stats = (SiteStats) XMLs.decode(statsXml);
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                logger.error("Failed to load stats from " + statsFile + ", reset the stats.", e);
                stats = new SiteStats();
            }
        else
            stats = new SiteStats();
        return stats;
    }

}
