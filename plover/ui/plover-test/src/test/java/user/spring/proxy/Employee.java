package user.spring.proxy;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Employee {

    private String name;
    private String empId;

    @Inject
    private IAddress address;

    public Employee() {
        System.out.println("Employee Constructor");
    }

    public IAddress getAddress() {
        return address;
    }

    public void setAddress(IAddress address) {
        this.address = address;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", empId=" + empId + ", address=" + address + "]";
    }

}
