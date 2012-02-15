package com.bee32.plover.arch.logging;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.spi.ThrowableInformation;

public class ExceptionLog {

    LinkedList<ExceptionLogEntry> entries;
    int maxSize;

    public ExceptionLog(int size) {
        if (size < 1)
            throw new IllegalArgumentException("size must be positive: " + size);
        this.entries = new LinkedList<ExceptionLogEntry>();
        this.maxSize = size;
    }

    public void addException(Object message, ThrowableInformation ti) {
        if (ti == null)
            throw new NullPointerException("ti");
        ExceptionLogEntry entry = new ExceptionLogEntry(new Date(), message, ti.getThrowable(), ti.getThrowableStrRep());
        entries.addLast(entry);
        while (entries.size() > maxSize)
            entries.removeFirst();
    }

    public LinkedList<ExceptionLogEntry> getEntries() {
        return entries;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

}
