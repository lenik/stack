package com.bee32.sem.company.dto;

import com.bee32.icsf.principal.dto.AbstractUserDto;
import com.bee32.sem.company.entity.Employee;

public class EmployeeDTO
        extends AbstractUserDto<Employee> {

    private static final long serialVersionUID = 1L;

    public EmployeeDTO() {
        super();
    }

    public EmployeeDTO(Employee entity) {
        super(entity);
    }

}
