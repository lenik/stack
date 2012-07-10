package com.bee32.sem.wage.entity;

import java.util.List;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.UIEntity;
import com.bee32.sem.hr.entity.EmployeeInfo;

/**
 * 基础工资
 */
@Entity
public class BaseSalary
        extends UIEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 按天计/按月计
     */
    enum Type {
        month, day
    }

    Type type;
    EmployeeInfo employee;
    boolean overtime;
    JobTitleSubsidy titleSubsidy;
    JobPostSubsidy postSubsidy;
    EducationSubsidy educationSubsidy;
    List<OtherSubsidy> otherSubsidies;


    @Override
    public Long getId() {
        return null;
    }

    @Override
    protected void setId(Long id) {
    }

}
