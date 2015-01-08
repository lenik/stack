package com.bee32.zebra.tk.util;

public class Counters {

    public static String displayName(String key) {
        switch (key) {
        case "total":
            return "总计";
        case "valid":
            return "有效";
        case "used":
            return "在用";
        case "locked":
            return "锁定";
        case "running":
            return "进行中";
        case "shipping":
            return "在途";
        case "ordered":
            return "下单";
        case "delivered":
            return "已出货";
        }
        return key;
    }

}
