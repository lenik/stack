package com.bee32.ape.engine.identity;

public class ActivitiOrderUtils {

    public static boolean isAscending(String orderBy) {
        if (orderBy == null)
            return true;

        orderBy = orderBy.trim();
        int pos = orderBy.indexOf(' ');
        if (pos != -1) {
            switch (orderBy.substring(pos + 1).trim().toLowerCase()) {
            case "desc":
                return false;
            }
        }
        return true;
    }

    public static String getOrderColumn(String orderBy) {
        if (orderBy == null)
            return null;

        orderBy = orderBy.trim();
        int pos = orderBy.indexOf(' ');
        String column;
        if (pos == -1) {
            column = orderBy;
        } else {
            column = orderBy.substring(0, pos);
        }

        return column;
    }

}
