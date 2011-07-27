package com.bee32.sem.inventory.entity;

import java.util.Date;

public class LocalDateUtil {

    public static Date truncate(Date date) {
        long time = date.getTime();
        time -= time % 86400000;
        return new Date(time);
    }

}
