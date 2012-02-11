package com.bee32.plover.arch.util.dto;

public interface Fmask {

    int F_BASIC = 0x00000000;
    int F_MORE_USER = 0x0000ffff;
    int F_MORE_SYS = 0x00ff0000;
    int F_MORE = F_MORE_USER | F_MORE_SYS;
    int F_EXT = 0xff000000;

}
