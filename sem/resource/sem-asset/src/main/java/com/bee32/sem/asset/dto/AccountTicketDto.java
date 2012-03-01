package com.bee32.sem.asset.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountTicketDto
        extends ProcessEntityDto<AccountTicket> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 0x10000;

    List<AccountTicketItemDto> items;
    BudgetRequestDto request;

    @Override
    protected void _marshal(AccountTicket source) {
        if (selection.contains(ITEMS))
            items = marshalList(AccountTicketItemDto.class, source.getItems());
        else
            items = new ArrayList<AccountTicketItemDto>();

        request = mref(BudgetRequestDto.class, source.getRequest());
    }

    @Override
    protected void _unmarshalTo(AccountTicket target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        merge(target, "request", request);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<AccountTicketItemDto> getItems() {
        return items;
    }

    public void setItems(List<AccountTicketItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(AccountTicketItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(AccountTicketItemDto item) {
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

    /**
     * 判断借贷是否相等
     */
    public boolean isDebitCreditEqual()
            throws FxrQueryException {
        BigDecimal debitTotal = BigDecimal.ZERO;
        BigDecimal creditTotal = BigDecimal.ZERO;
        for (AccountTicketItemDto item : items) {
            if (item.isDebitSide()) {
                MutableMCValue xx = item.getValue();
                debitTotal = debitTotal.add(item.getValue().getNativeValue(getCreatedDate()).abs());
            } else {
                creditTotal = creditTotal.add(item.getValue().getNativeValue(getCreatedDate()).abs());
            }
        }
        return debitTotal.equals(creditTotal);
    }

}
