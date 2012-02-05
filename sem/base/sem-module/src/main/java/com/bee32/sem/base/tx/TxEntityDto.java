package com.bee32.sem.base.tx;

import java.io.Serializable;
import java.util.Map;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;

public abstract class TxEntityDto<E extends TxEntity>
        extends MomentIntervalDto<E> {

    private static final long serialVersionUID = 1L;

    String serial;

    public TxEntityDto() {
        super();
    }

    public TxEntityDto(int fmask) {
        super(fmask);
    }

    @Override
    public TxEntityDto<E> populate(Object source) {
        if (source instanceof TxEntityDto) {
            TxEntityDto<?> o = (TxEntityDto<?>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(TxEntityDto<?> o) {
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
