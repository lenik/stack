package com.bee32.sem.base.tx;

import java.util.Map;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.MomentIntervalDto;

public abstract class TxEntityDto<E extends TxEntity>
        extends MomentIntervalDto<E> {

    private static final long serialVersionUID = 1L;

    String name;

    public TxEntityDto() {
        super();
    }

    public TxEntityDto(int selection) {
        super(selection);
    }

    protected void __marshal(E source) {
        super.__marshal(source);
        name = source.getName();
    }

    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setName(name);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        name = map.getString("name");
    }

    @Override
    protected void __export(Map<String, Object> map) {
        super.__export(map);
        map.put("name", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
