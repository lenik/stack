package com.bee32.plover.faces.diag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.AbstractNonNullComparator;
import javax.free.Pair;

import com.bee32.plover.faces.view.PerView;
import com.bee32.plover.faces.view.ViewBean;

@PerView
public class ThreadDiagBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public List<Entry<Thread, StackTraceElementWrapper[]>> getStackTraces() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        List<Entry<Thread, StackTraceElementWrapper[]>> list = new ArrayList<>();

        for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] elements = entry.getValue();
            StackTraceElementWrapper[] wrappers = new StackTraceElementWrapper[elements.length];
            for (int i = 0; i < elements.length; i++)
                wrappers[i] = new StackTraceElementWrapper(elements[i]);
            Pair<Thread, StackTraceElementWrapper[]> wrapped = new Pair<>(thread, wrappers);
            list.add(wrapped);
        }

        Collections.sort(list, ThreadNameComparator.INSTANCE);
        return list;
    }

}

class ThreadNameComparator
        extends AbstractNonNullComparator<Entry<Thread, ?>> {

    @Override
    public int compareNonNull(Entry<Thread, ?> o1, Entry<Thread, ?> o2) {
        Thread t1 = o1.getKey();
        Thread t2 = o2.getKey();
        int cmp = t1.getName().compareTo(t2.getName());
        if (cmp != 0)
            return cmp;
        if (t1.equals(t2))
            return 0;
        int hc = t1.hashCode() - t2.hashCode();
        if (hc != 0)
            return hc;
        return -1;
    }

    public static final ThreadNameComparator INSTANCE = new ThreadNameComparator();

}