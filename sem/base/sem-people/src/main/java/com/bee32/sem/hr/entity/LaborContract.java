package com.bee32.sem.hr.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.sem.misc.Contract;

/**
 * 劳动合同
 *
 * 雇员和公司签订的劳动合同。
 */
@Entity
@DiscriminatorValue("LAB")
public class LaborContract
        extends Contract {

    private static final long serialVersionUID = 1L;

    EmployeeInfo employeeInfo;

    @Override
    public void populate(Object source) {
        if (source instanceof LaborContract)
            _populate((LaborContract) source);
        else
            super.populate(source);
    }

    protected void _populate(LaborContract o) {
        super._populate(o);
        this.employeeInfo = o.employeeInfo;
    }

    /**
     * 雇员
     *
     * 相关雇员。
     */
    @ManyToOne
    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }
}
