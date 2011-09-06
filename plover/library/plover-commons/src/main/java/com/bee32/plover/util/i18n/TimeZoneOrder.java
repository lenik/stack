package com.bee32.plover.util.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import javax.free.AbstractNonNullComparator;

public abstract class TimeZoneOrder
        extends AbstractNonNullComparator<TimeZone> {

    final List<TimeZone> timeZones;

    public TimeZoneOrder() {
        timeZones = new ArrayList<TimeZone>();
        for (String id : TimeZone.getAvailableIDs()) {
            TimeZone timeZone = TimeZone.getTimeZone(id);
            timeZones.add(timeZone);
        }
        Collections.sort(timeZones, this);
    }

    public List<TimeZone> list() {
        return timeZones;
    }

    private static class IdOrder
            extends TimeZoneOrder {

        @Override
        public int compareNonNull(TimeZone o1, TimeZone o2) {
            String id1 = o1.getID();
            String id2 = o2.getID();
            return id1.compareTo(id2);
        }

    }

    private static class NameOrder
            extends TimeZoneOrder {

        @Override
        public int compareNonNull(TimeZone o1, TimeZone o2) {
            String name1 = o1.getDisplayName();
            String name2 = o2.getDisplayName();
            return name1.compareTo(name2);
        }

    }

    private static class OffsetOrder
            extends TimeZoneOrder {

        @Override
        public int compareNonNull(TimeZone o1, TimeZone o2) {
            int cmp = o1.getRawOffset() - o2.getRawOffset();
            return cmp;
        }

    }

    public static final IdOrder ID = new IdOrder();
    public static final NameOrder NAME = new NameOrder();
    public static final OffsetOrder OFFSET = new OffsetOrder();

}
