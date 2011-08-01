package com.bee32.sem.inventory.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.dto.PersonDto;

public class StockWarehouseDto
        extends UIEntityDto<StockWarehouse, Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    String address;
    String phone;
    PersonDto manager;

    @Override
    protected void _marshal(StockWarehouse source) {
        this.name = source.getName();
        this.address = source.getAddress();
        this.phone = source.getPhone();
        this.manager = mref(PersonDto.class, source.getManager());
    }

    @Override
    protected void _unmarshalTo(StockWarehouse target) {
        target.setName(name);
        target.setAddress(address);
        target.setPhone(phone);
        merge(target, "manager", manager);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNodeText() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + getName() + "] ");
        if (getLabel() != null && !getLabel().isEmpty())
            sb.append(getLabel());
        if (getLabel() != null && !getLabel().isEmpty() && address != null && !address.isEmpty())
            sb.append(":");
        if (address != null && !address.isEmpty())
            sb.append(address);
        return sb.toString();
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        else
            return name;
    }

}
