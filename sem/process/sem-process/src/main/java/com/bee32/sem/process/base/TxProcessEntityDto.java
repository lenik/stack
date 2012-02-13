package com.bee32.sem.process.base;

import java.io.Serializable;
import java.util.Map;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;

public abstract class TxProcessEntityDto<E extends TxProcessEntity>
        extends ProcessEntityDto<E> {

    private static final long serialVersionUID = 1L;

    String serial;

    public TxProcessEntityDto() {
        super();
    }

    public TxProcessEntityDto(int fmask) {
        super(fmask);
    }

    @Override
    public TxProcessEntityDto<E> populate(Object source) {
        if (source instanceof TxProcessEntityDto) {
            TxProcessEntityDto<?> o = (TxProcessEntityDto<?>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(TxProcessEntityDto<?> o) {
        super._populate(o);
        serial = o.serial;
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        serial = source.getSerial();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setSerial(serial);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        serial = map.getString("serial");
    }

    @Override
    protected void __export(Map<String, Object> map) {
        super.__export(map);
        map.put("serial", serial);
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        if (serial != null && serial.isEmpty())
            serial = null;
        this.serial = serial;
    }

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

}
