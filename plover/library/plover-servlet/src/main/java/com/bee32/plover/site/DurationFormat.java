package com.bee32.plover.site;

public class DurationFormat {

    public static final String format(Long duration) {
        if (duration == null || duration == 0)
            return "";

        // int ms = (int) (duration % 100);
        duration /= 1000;
        int secs = (int) (duration % 60);
        duration /= 60;
        int mins = (int) (duration % 60);
        duration /= 60;
        int hours = duration.intValue();
        return String.format("%d:%02d:%02d", hours, mins, secs);
    }

}
