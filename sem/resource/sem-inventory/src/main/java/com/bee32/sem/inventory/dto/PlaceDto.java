package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.StockLocation;

public class PlaceDto
        extends TreeEntityDto<StockLocation, Integer, PlaceDto> {

    private static final long serialVersionUID = 1L;

    public PlaceDto() {
        super();
    }

    public PlaceDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(StockLocation source) {
    }

    @Override
    protected void _unmarshalTo(StockLocation target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
