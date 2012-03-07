package com.bee32.sem.people.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ContactCategories
        extends StandardSamples {

    public final ContactCategory NORMAL = new ContactCategory("NORM", "常用");
    public final ContactCategory HOME = new ContactCategory("HOME", "家庭");
    public final ContactCategory WORK = new ContactCategory("WORK", "工作");
    public final ContactCategory OUT = new ContactCategory("OUT", "出差");
    public final ContactCategory OTHER = new ContactCategory("OTHE", "其它");

}
