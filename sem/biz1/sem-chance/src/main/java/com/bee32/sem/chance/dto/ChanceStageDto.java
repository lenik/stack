package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.chance.entity.ChanceStage;

public class ChanceStageDto
        extends SimpleNameDictDto<ChanceStage> {

    private static final long serialVersionUID = 1L;

    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    protected void _marshal(ChanceStage source) {
        super._marshal(source);
        this.order = source.getOrder();
    }

    @Override
    protected void _unmarshalTo(ChanceStage target) {
        super._unmarshalTo(target);
        target.setOrder(order);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

    public String getIdX() {
        if (getId() == null)
            return "";
        else
            return getId();
    }

    public void setIdX(String idx) {
        if (idx.isEmpty())
            setId(null);
        else {
            setId(idx);
        }
    }
}
