package com.bee32.sem.process.verify.impl;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.IEntity;

/**
 * 审核步骤
 */
public class PassStep
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /** 责任人 */
    private IPrincipal responsible;

    /** 步骤可选 */
    public boolean optional;

    public PassStep(IPrincipal responsible, boolean optional) {
        if (responsible == null)
            throw new NullPointerException("responsible");
        this.optional = optional;
    }

    @Override
    public Integer getPrimaryKey() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IPrincipal getResponsible() {
        return responsible;
    }

    public void setResponsible(IPrincipal responsible) {
        this.responsible = responsible;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public String toString() {
        String description = String.valueOf(responsible);
        if (optional)
            description += "?";
        return description;
    }

}
