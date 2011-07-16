package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.dto.PersonDto;

public class StockWarehouseDto
        extends EntityDto<StockWarehouse, Integer> {

    private static final long serialVersionUID = 1L;

    String address;
    String phone;
    PersonDto manager;

    @Override
    protected void _marshal(StockWarehouse source) {
        this.address = source.getAddress();
        this.phone = source.getPhone();
        this.manager = mref(PersonDto.class, source.getManager());
    }

    @Override
    protected void _unmarshalTo(StockWarehouse target) {
        target.setAddress(address);
        target.setPhone(phone);
        merge(target, "manager", manager);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonDto getManager() {
        return manager;
    }

    public void setManager(PersonDto manager) {
        this.manager = manager;
    }

}
