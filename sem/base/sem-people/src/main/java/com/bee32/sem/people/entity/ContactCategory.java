package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * 联系信息分组
 */
@Entity
public class ContactCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ContactCategory() {
        super();
    }

    public ContactCategory(String name, String label) {
        super(name, label);
    }

    public ContactCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public static ContactCategory NORMAL = new ContactCategory("NORM", "常用");
    public static ContactCategory HOME = new ContactCategory("HOME", "家庭");
    public static ContactCategory WORK = new ContactCategory("WORK", "工作");
    public static ContactCategory OUT = new ContactCategory("OUT", "出差");
    public static ContactCategory OTHER = new ContactCategory("OTHE", "其它");

}
