package com.bee32.sem.asset.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountInit;
import com.bee32.sem.base.tx.TxEntityDto;

public class AccountInitDto
        extends TxEntityDto<AccountInit> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    List<AccountInitItemDto> items;

    private AccountInitDto() {
        super();
    }

    private AccountInitDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountInit source) {
        if (selection.contains(ITEMS))
            items = marshalList(AccountInitItemDto.class, source.getItems());
        else
            items = new ArrayList<AccountInitItemDto>();
    }

    @Override
    protected void _unmarshalTo(AccountInit target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<AccountInitItemDto> getItems() {
        return items;
    }

    public void setItems(List<AccountInitItemDto> items) {
        this.items = items;
    }

}
