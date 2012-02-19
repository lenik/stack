package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 洽谈类型
 */
@Entity
@Blue
public class ChanceActionStyle
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceActionStyle() {
        super();
    }

    public ChanceActionStyle(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceActionStyle(String name, String label) {
        super(name, label);
    }

    public static ChanceActionStyle TELEPHONE = new ChanceActionStyle("TELE", "电话洽谈");
    public static ChanceActionStyle TALK = new ChanceActionStyle("TALK", "面谈");
    public static ChanceActionStyle WALK = new ChanceActionStyle("WALK", "走访");
    public static ChanceActionStyle INTERNET = new ChanceActionStyle("INTE", "通过互联网");
    public static ChanceActionStyle MAIL = new ChanceActionStyle("MAIL", "信件洽谈");
    public static ChanceActionStyle OTHER = new ChanceActionStyle("OTHE", "其它方式");

}
