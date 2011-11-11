package com.bee32.sem.asset.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.Account;
import com.bee32.sem.base.tx.TxEntityDto;

public class AccountDto extends TxEntityDto<Account> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    List<AccountItemDto> items;
    BudgetRequestDto request;

    @Override
    protected void _marshal(Account source) {
        if (selection.contains(ITEMS))
            items = marshalList(AccountItemDto.class, source.getItems());
        else
            items = new ArrayList<AccountItemDto>();

        request = mref(BudgetRequestDto.class, source.getRequest());
    }

    @Override
    protected void _unmarshalTo(Account target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        merge(target, "request", request);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public List<AccountItemDto> getItems() {
        return items;
    }

    public void setItems(List<AccountItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }


    public synchronized void addItem(AccountItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(AccountItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        // item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }


    public BudgetRequestDto getRequest() {
        return request;
    }

    public void setRequest(BudgetRequestDto request) {
        this.request = request;
    }


}
