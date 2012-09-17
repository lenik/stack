package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 组织机构类型
 *
 * 用于组织机构分类。
 */
@Entity
public class OrgType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public OrgType() {
        super();
    }

    public OrgType(String name, String label, String description) {
        super(name, label, description);
    }

    public OrgType(String name, String label) {
        super(name, label);
    }

}
