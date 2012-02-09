package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountInitItem;
import com.bee32.sem.asset.entity.AccountTicketItem;

public class AccountInitItemDto
        extends AccountTicketItemDto {

    private static final long serialVersionUID = 1L;

    AccountInitDto parent;

    @Override
    protected void _marshal(AccountTicketItem _source) {
        super._marshal(_source);
        AccountInitItem source = (AccountInitItem) _source;
        parent = mref(AccountInitDto.class, source.getParent());
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem _target) {
        super._unmarshalTo(_target);
        AccountInitItem target = (AccountInitItem) _target;
        merge(target, "parent", parent);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

}
