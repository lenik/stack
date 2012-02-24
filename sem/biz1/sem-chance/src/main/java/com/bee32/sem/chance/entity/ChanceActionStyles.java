package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.util.StandardSamples;

public class ChanceActionStyles
        extends StandardSamples {

    public final ChanceActionStyle TELEPHONE = new ChanceActionStyle("TELE", "电话洽谈");
    public final ChanceActionStyle TALK = new ChanceActionStyle("TALK", "面谈");
    public final ChanceActionStyle WALK = new ChanceActionStyle("WALK", "走访");
    public final ChanceActionStyle INTERNET = new ChanceActionStyle("INTE", "通过互联网");
    public final ChanceActionStyle MAIL = new ChanceActionStyle("MAIL", "信件洽谈");
    public final ChanceActionStyle OTHER = new ChanceActionStyle("OTHE", "其它方式");

}
